package home.project.domain.elasticsearch;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(indexName = "coupons")
public class CouponDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String name;

    @Field(type = FieldType.Integer)
    private Integer discountRate = 0;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime startDate;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime endDate;

    @Field(type = FieldType.Text)
    private String assignBy;

    @Field(type = FieldType.Nested)
    private List<ProductCouponInfo> productCoupons = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<MemberCouponInfo> memberCoupons = new ArrayList<>();

    @Getter
    @Setter
    public static class ProductCouponInfo {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
        private LocalDateTime issuedAt;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
        private LocalDateTime usedAt;

        @Field(type = FieldType.Boolean)
        private boolean isUsed;

        @Field(type = FieldType.Long)
        private Long productId;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String productName;

        @Field(type = FieldType.Keyword)
        private String productNum;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String brand;

        @Field(type = FieldType.Nested)
        private CategoryInfo category;
    }

    @Getter
    @Setter
    public static class MemberCouponInfo {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
        private LocalDateTime issuedAt;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
        private LocalDateTime usedAt;

        @Field(type = FieldType.Boolean)
        private boolean isUsed;

        @Field(type = FieldType.Long)
        private Long memberId;

        @Field(type = FieldType.Keyword)
        private String email;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String name;

        @Field(type = FieldType.Keyword)
        private String grade;
    }

    @Getter
    @Setter
    public static class CategoryInfo {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String code;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String name;

        @Field(type = FieldType.Integer)
        private Integer level;

        @Field(type = FieldType.Long)
        private Long parentId;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String parentName;

        @Field(type = FieldType.Keyword)
        private String parentCode;
    }
}