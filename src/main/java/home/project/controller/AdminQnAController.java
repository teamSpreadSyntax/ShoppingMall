package home.project.controller;

import home.project.dto.responseDTO.QnADetailResponse;
import home.project.response.CustomResponseEntity;
import home.project.service.QnAService;
import home.project.util.PageUtil;
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

@Tag(name = "관리자 QnA", description = "관리자용 QnA 관련 API입니다")
@RequestMapping(path = "/api/admin/qna")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/InternalServerErrorResponseSchema")))
})
@RequiredArgsConstructor
@RestController
public class AdminQnAController {

    private final QnAService qnAService;
    private final PageUtil pageUtil;

    @Operation(summary = "관리자 QnA 답변 작성 메서드", description = "관리자가 QnA 답변을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/QnADetailResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @PostMapping("/answer")
    @SecurityRequirement(name = "bearerAuth")
    public CustomResponseEntity<?> addAnswer(@RequestParam Long qnAId, @RequestParam String answer) {
        QnADetailResponse response = qnAService.addAnswer(qnAId, answer);
        return new CustomResponseEntity<>(response, "답변이 등록되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "관리자 QnA 답변 수정 메서드", description = "관리자가 QnA 답변을 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/QnADetailResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @PutMapping("/update")
    @SecurityRequirement(name = "bearerAuth")
    public CustomResponseEntity<?> updateAnswer(@RequestParam Long qnAId, @RequestParam String answer) {
        QnADetailResponse response = qnAService.updateAnswer(qnAId, answer);
        return new CustomResponseEntity<>(response, "답변이 수정되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "관리자 QnA 답변 삭제 메서드", description = "관리자가 QnA 답변을 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/GeneralSuccessResponseSchema"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/NotFoundResponseSchema")))
    })
    @DeleteMapping("/delete")
    @SecurityRequirement(name = "bearerAuth")
    public CustomResponseEntity<?> deleteAnswer(@RequestParam Long qnAId) {
        qnAService.deleteAnswer(qnAId);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("successMessage", qnAId + "번 QnA의 답변이 삭제되었습니다.");
        return new CustomResponseEntity<>(responseMap, "답변 삭제 성공", HttpStatus.OK);
    }

    @Operation(summary = "관리자 전체 QnA 답변 대기 목록 조회 메서드", description = "답변 대기중인 전체 QnA 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/PagedQnAListResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(ref = "#/components/schemas/BadRequestResponseSchema")))
    })
    @GetMapping("/waiting")
    @SecurityRequirement(name = "bearerAuth")
    public CustomResponseEntity<?> findAllWaitingQnA(
            @PageableDefault(page = 1, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createAt", direction = Sort.Direction.DESC)
            }) @ParameterObject Pageable pageable) {

        pageable = pageUtil.pageable(pageable);

        Page<QnADetailResponse> pagedQnA = qnAService.findAllWaitingQnA(pageable);

        long totalCount = pagedQnA.getTotalElements();
        int page = pagedQnA.getNumber();

        return new CustomResponseEntity<>(pagedQnA.getContent(), "답변 대기중인 QnA 목록입니다.", HttpStatus.OK, totalCount, page);
    }
}