package home.project.domain.elasticsearch;

import home.project.domain.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(indexName = "orders")
public class OrderDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String orderNum;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    @Field(type = FieldType.Long)
    private Long amount;

    @Field(type = FieldType.Long)
    private Long pointsUsed;

    @Field(type = FieldType.Long)
    private Long pointsEarned;

    @Field(type = FieldType.Nested)
    private Member member;

    @Field(type = FieldType.Nested)
    private Shipping shipping;

    @Field(type = FieldType.Nested)
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Getter
    @Setter
    public static class Member {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String email;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String name;

        @Field(type = FieldType.Keyword)
        private String phone;

        @Field(type = FieldType.Keyword)
        private MemberGenderType gender;

        @Field(type = FieldType.Date, format = DateFormat.date)
        private LocalDate birthDate;

        @Field(type = FieldType.Keyword)
        private String defaultAddress;

        @Field(type = FieldType.Keyword)
        private String secondAddress;

        @Field(type = FieldType.Keyword)
        private String thirdAddress;

        @Field(type = FieldType.Keyword)
        private RoleType role;

        @Field(type = FieldType.Long)
        private Long accumulatedPurchase;

        @Field(type = FieldType.Keyword)
        private MemberGradeType grade;

        @Field(type = FieldType.Long)
        private Long point;

        @Field(type = FieldType.Nested)
        private List<MemberCoupon> memberCoupons = new ArrayList<>();

        @Field(type = FieldType.Nested)
        private List<MemberEvent> memberEvents = new ArrayList<>();

        @Field(type = FieldType.Nested)
        private List<WishList> wishLists = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class MemberCoupon {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long memberId;  // 순환참조 방지

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime issuedAt;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime usedAt;

        @Field(type = FieldType.Boolean)
        private boolean isUsed;

        @Field(type = FieldType.Nested)
        private Coupon coupon;
    }

    @Getter
    @Setter
    public static class MemberEvent {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long memberId;  // 순환참조 방지

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;

        @Field(type = FieldType.Nested)
        private Event event;
    }

    @Getter
    @Setter
    public static class WishList {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long memberId;  // 순환참조 방지

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createAt;

        @Field(type = FieldType.Nested)
        private Product product;
    }

    @Getter
    @Setter
    public static class Shipping {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long orderId;  // 순환참조 방지

        @Field(type = FieldType.Keyword)
        private DeliveryType deliveryType;

        @Field(type = FieldType.Keyword)
        private String deliveryNum;

        @Field(type = FieldType.Keyword)
        private String deliveryAddress;

        @Field(type = FieldType.Keyword)
        private String arrivingDate;

        @Field(type = FieldType.Keyword)
        private String arrivedDate;

        @Field(type = FieldType.Long)
        private Long deliveryCost;

        @Field(type = FieldType.Keyword)
        private DeliveryStatusType deliveryStatus;
    }

    @Getter
    @Setter
    public static class ProductOrder {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long orderId;  // 순환참조 방지

        @Field(type = FieldType.Integer)
        private Integer quantity;

        @Field(type = FieldType.Long)
        private Long price;

        @Field(type = FieldType.Keyword)
        private DeliveryStatusType deliveryStatus;

        @Field(type = FieldType.Nested)
        private Product product;
    }

    @Getter
    @Setter
    public static class Product {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String name;

        @Field(type = FieldType.Keyword)
        private String brand;

        @Field(type = FieldType.Keyword)
        private String productNum;

        @Field(type = FieldType.Long)
        private Long stock;

        @Field(type = FieldType.Long)
        private Long soldQuantity;

        @Field(type = FieldType.Long)
        private Long price;

        @Field(type = FieldType.Integer)
        private Integer discountRate;

        @Field(type = FieldType.Long)
        private Long defectiveStock;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String description;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createAt;

        @Field(type = FieldType.Keyword)
        private String imageUrl;

        @Field(type = FieldType.Nested)
        private Category category;

        @Field(type = FieldType.Nested)
        private List<ProductCoupon> productCoupons = new ArrayList<>();

        @Field(type = FieldType.Nested)
        private List<ProductEvent> productEvents = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class ProductCoupon {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long productId;  // 순환참조 방지

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime issuedAt;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime usedAt;

        @Field(type = FieldType.Boolean)
        private boolean isUsed;

        @Field(type = FieldType.Nested)
        private Coupon coupon;
    }

    @Getter
    @Setter
    public static class ProductEvent {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Long)
        private Long productId;  // 순환참조 방지

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;

        @Field(type = FieldType.Nested)
        private Event event;
    }

    @Getter
    @Setter
    public static class Category {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String code;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Integer)
        private Integer level;

        @Field(type = FieldType.Long)
        private Long parentId;  // 순환참조 방지
    }

    @Getter
    @Setter
    public static class Coupon {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Integer)
        private Integer discountRate;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startDate;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endDate;

        @Field(type = FieldType.Keyword)
        private String assignBy;
    }

    @Getter
    @Setter
    public static class Event {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Integer)
        private Integer discountRate;

        @Field(type = FieldType.Text, analyzer = "nori")
        private String description;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startDate;

        @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endDate;

        @Field(type = FieldType.Keyword)
        private String image;
    }
}