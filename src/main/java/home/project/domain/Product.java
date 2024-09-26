package home.project.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_num" })})
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "product_num")
    private String productNum;

    @Check(constraints = "stock >= 0")
    @Column(name = "stock")
    private Long stock;

    @Check(constraints = "sold_Quantity >= 0")
    @Column(name = "sold_Quantity")
    private Long soldQuantity = 0L;

}
