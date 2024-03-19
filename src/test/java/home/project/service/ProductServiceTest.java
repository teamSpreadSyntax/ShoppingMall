package home.project.service;

import home.project.domain.Product;
import home.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProductServiceTest {
    @LocalServerPort
    int port;

    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;

    @BeforeEach
    public  void clearData(){
        productRepository.deleteAll();
    }

    @DisplayName("제품등록테스트 케이스")
    @Test
    void join() {
        //given
        Product product1 = new Product();
        product1.setBrand("qwer");
        product1.setCategory("qert");
        product1.setImage("1234");
        product1.setName("나이키 플러워");
        product1.setStock(4545L);
        //when
        productService.join(product1);
        //then
        Product product2 = productService.findByname(product1.getName()).get();
        Assertions.assertThat(product1.getName()).isEqualTo(product2.getName());

    }

    @Test
    void findAll() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("1234.img");
        product1.setName("나이키 플러워");
        product1.setStock(100L);
        productService.join(product1);

        Product product2 = new Product();
        product2.setBrand("아디다스");
        product2.setCategory("팬츠");
        product2.setImage("123.img");
        product2.setName("축구화");
        product2.setStock(45L);
        productService.join(product2);
        //when
        List<Product> product = productService.findAll();
        //then
        Assertions.assertThat(product.size()).isEqualTo(2);

    }

    @Test
    void findByname() {
        //given
        Product product1 = new Product();
        product1.setBrand("qwer");
        product1.setCategory("qert");
        product1.setImage("1234");
        product1.setName("나이키 플러워");
        product1.setStock(4545L);
        productService.join(product1);
        //when
        Product findByName = productService.findByname(product1.getName()).get();
        //then
        Assertions.assertThat(product1.getName()).isEqualTo(findByName.getName());
    }

    @Test
    void findByBrand() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        //given
        Product product2 = new Product();
        product2.setBrand("나이키");
        product2.setCategory("티셔츠");
        product2.setImage("1234.img");
        product2.setName("나이키 T");
        product2.setStock(20L);
        productService.join(product2);
        //when
        List<Product> findByBrand = productService.findByBrand("나이키").get();
        //then
        Assertions.assertThat(findByBrand.size()).isEqualTo(2);
    }

    @Test
    void findByCategory() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        //given
        Product product2 = new Product();
        product2.setBrand("아디다스");
        product2.setCategory("바지");
        product2.setImage("1234.img");
        product2.setName("아디다스 트레이닝");
        product2.setStock(20L);
        productService.join(product2);
        //when
        List<Product> findByCategory = productService.findByCategory("바지").get();
        //then
        Assertions.assertThat(findByCategory.size()).isEqualTo(2);
    }

    @Test
    void detailProduct() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        //when
        Product detailProduct = productService.DetailProduct(product1.getId()).get();
        //then
        Assertions.assertThat(detailProduct).isEqualTo(product1);
    }

    @Test
    void update() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        //when
        product1.setCategory("카고바지");
        productService.update(product1);
        //then
        Product productUpdate = productService.findByname(product1.getName()).get();
        Assertions.assertThat(productUpdate.getName()).isEqualTo(product1.getName());

    }

    @Test
    void deleteById() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        //when
        Product findProduct = productService.findByname(product1.getName()).get();
        productService.deleteById(findProduct.getId());
        //then
        assertThrows(IllegalStateException.class, () -> {productService.findByname
                (product1.getName()).orElseThrow(() -> new IllegalStateException("존재하지 않는 제품입니다."));});
    }

    @Test
    void brandList() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        productService.join(product1);
        Product product2 = new Product();
        product2.setBrand("아디다스");
        product2.setCategory("신발");
        product2.setImage("123456.img");
        product2.setName("아디다스 신발");
        product2.setStock(20L);
        productService.join(product2);
        Product product3 = new Product();
        product3.setBrand("뉴발란스");
        product3.setCategory("바지");
        product3.setImage("12345.img");
        product3.setName("뉴발란스 모자");
        product3.setStock(30L);
        productService.join(product3);
        //when
        List<String> productList = productService.brandList();
        //then
        Assertions.assertThat(productList.size()).isEqualTo(3);
        assertTrue(productList.contains("나이키")); // 리스트에 "나이키" 브랜드가 포함되어 있는지 확인
        assertTrue(productList.contains("아디다스")); // 리스트에 "아디다스" 브랜드가 포함되어 있는지 확인
        assertTrue(productList.contains("뉴발란스")); // 리스트에 "뉴발란스" 브랜드가 포함되어 있는지 확인
    }

    @Test
    void increaseStock() {
        //given
        Product product1 = new Product();
        product1.setBrand("나이키");
        product1.setCategory("바지");
        product1.setImage("123.img");
        product1.setName("나이키 플러워");
        product1.setStock(10L);
        product1.setSelledcount(50L);
        productService.join(product1);
        //when
        Product update = productService.increaseStock(product1.getId(),10L);
        //then
        Assertions.assertThat(update.getStock()).isEqualTo(20L);
        Assertions.assertThat(update.getSelledcount()).isEqualTo(50L);
    }

    @Test
    void decreaseStock() {
    }
}