package home.project.controller;

import home.project.domain.DeliveryStatusType;
import home.project.dto.requestDTO.CreateShippingRequestDTO;
import home.project.dto.responseDTO.ShippingResponse;
import home.project.response.CustomResponseEntity;
import home.project.service.ShippingService;
import home.project.util.PageUtil;
import home.project.util.StringBuilderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "배송", description = "배송관련 API입니다")
@RequestMapping("/api/shipping")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/InternalServerErrorResponseSchema")))
})
@RequiredArgsConstructor
@RestController
public class ShippingController {

    private final ShippingService shippingService;
    private final PageUtil pageUtil;


    @Operation(summary = "배송, 주문 상태 업데이트 메서드", description = "배송, 주문 상태 업데이트 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/VerifyResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/MemberValidationFailedResponseSchema"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/UnauthorizedResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))

    })
    @PostMapping("/update")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateShipping(@RequestParam("shippingId") Long shippingId,
                                            @RequestParam("deliveryStatusType") DeliveryStatusType deliveryStatusType) {

        ShippingResponse shippingResponse = shippingService.update(shippingId, deliveryStatusType);

        String successMessage = shippingResponse.getDeliveryStatusType().getDescription() + "(으)로 배송(주문)상태가 변경되었습니다.";

        return new CustomResponseEntity<>(shippingResponse, successMessage, HttpStatus.OK);
    }

    @Operation(summary = "id로 배송 조회(+내배송조회) 메서드", description = "id로 배송 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/ProductResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @GetMapping("/shipping")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findShippingById(@RequestParam("shippingId") Long shippingId) {
        ShippingResponse shippingResponse = shippingService.findByIdReturnShippingResponse(shippingId);
        String successMessage = shippingId + "에 해당하는 배송 입니다.";
        return new CustomResponseEntity<>(shippingResponse, successMessage, HttpStatus.OK);
    }

    @Operation(summary = "관리자를 위한 전체 배송 조회 메서드", description = "관리자를 위한 전체 배송 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedProductListResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema")))
    })
    @GetMapping("/admin/shippings")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> findAll(
            @PageableDefault(page = 1, size = 5)
            @SortDefault.SortDefaults(
                    {@SortDefault(sort = "deliveryNum", direction = Sort.Direction.ASC)})
            @ParameterObject Pageable pageable) {
        pageable = pageUtil.pageable(pageable);
        Page<ShippingResponse> pagedShipping = shippingService.findAll(pageable);

        long totalCount = pagedShipping.getTotalElements();

        int page = pagedShipping.getNumber();

        String successMessage = "전체 배송입니다.";

        return new CustomResponseEntity<>(pagedShipping.getContent(), successMessage, HttpStatus.OK, totalCount, page);
    }

    @Operation(summary = "검색어로 배송 조회 메서드", description = "배송번호, 배송날짜, 상품 품번, 회원 이메일 및 일반 검색어로 배송정보를 조회합니다. 모든 조건을 만족하는 배송정보를 조회합니다. 검색어가 없으면 전체 쿠폰을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedProductListResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @GetMapping("/search")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> searchShipping(
            @RequestParam(value = "deliveryNum", required = false) String deliveryNum,
            @RequestParam(value = "shippingDate", required = false) String shippingDate,
            @RequestParam(value = "deliveryAddress", required = false) String productNumber,
            @RequestParam(value = "memberEmail", required = false) String email,
            @RequestParam(value = "content", required = false) String content,
            @PageableDefault(page = 1, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "delivery_num", direction = Sort.Direction.ASC)
            }) @ParameterObject Pageable pageable) {
        pageable = pageUtil.pageable(pageable);

        Page<ShippingResponse> pagedShippingResponse = shippingService.findShippings(deliveryNum, shippingDate, productNumber, email, content, pageable);

        String successMessage = StringBuilderUtil.buildShippingSearchCriteria(deliveryNum, shippingDate, productNumber, email, content, pagedShippingResponse);

        long totalCount = pagedShippingResponse.getTotalElements();
        int page = pagedShippingResponse.getNumber();

        return new CustomResponseEntity<>(pagedShippingResponse.getContent(), successMessage, HttpStatus.OK, totalCount, page);

    }






}