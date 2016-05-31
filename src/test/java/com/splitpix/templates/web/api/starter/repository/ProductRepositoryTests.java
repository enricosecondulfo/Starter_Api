package com.splitpix.templates.web.api.starter.repository;

import com.splitpix.templates.web.api.starter.domain.BaseDomain;
import com.splitpix.templates.web.api.starter.domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRepositoryTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void init() {
       this.mongoTemplate.insertAll(this.createProducts());
    }

    @Test
    public void findAllShouldReturnProducts() {
        Stream<Product> products = this.productRepository.findAll();
        Product product = products.findFirst().orElse(null);

        assertThat(product).isNotEqualTo(null);
        assertThat(product.getName()).isEqualTo("test 0");
    }

    @Test
    public void findAllWithLimitShouldReturnProducts() {
        Supplier<Stream<Product>> productsSupplier = () -> this.productRepository.findAll(10);
        Product product = productsSupplier.get()
                .findFirst().orElse(null);

        assertThat(productsSupplier.get().count()).isEqualTo(10);
        assertThat(product.getName()).isEqualTo("test 99");
    }

    @Test
    public void findAllWithSortAndLimitShouldReturnProducts() {
        // this.mongoTemplate.save(this.createProduct("test2"));

        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lastModifiedDate"),
            new Sort.Order(Sort.Direction.ASC, "createdDate"));

        Supplier<Stream<Product>> productsSupplier = () -> this.productRepository.findAll(sort, 2);
        Product product = productsSupplier.get()
            .findFirst().orElse(null);

        assertThat(productsSupplier.get().count()).isEqualTo(2);
        assertThat(product.getName()).isEqualTo("test 0");
    }

    @Test
    public void findAllWithSortLimitAndLastIdShouldReturnProducts() {
        // this.mongoTemplate.save(this.createProduct("test2"));

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "lastModifiedDate"),
                new Sort.Order(Sort.Direction.DESC, "createdDate"));

        Supplier<Stream<Product>> productsSupplier = () -> this.productRepository.findAll(sort, 10);
        Product product = productsSupplier.get()
                .findFirst().orElse(null);

        Supplier<Stream<Product>> nextingProductsSupplier = () -> this.productRepository.findAll(sort, 9, product.getId());

        assertThat(nextingProductsSupplier.get().count()).isGreaterThan(0);

        Iterator<Product> productsIterator = productsSupplier.get().skip(1).iterator();
        Iterator<Product> nextingProductsIterator = nextingProductsSupplier.get().iterator();

        while(productsIterator.hasNext() && nextingProductsIterator.hasNext()) {
            assertThat(productsIterator.next().getId()).isEqualTo(nextingProductsIterator.next().getId());
        }
    }

    @After
    public void destroy() {
        this.mongoTemplate.dropCollection(Product.class);
    }

    private List<Product> createProducts() {
        List<Product> products = new ArrayList<>();

        IntStream.range(0, 100).forEach(index -> {
            Product product = new Product();
            product.setName(String.format("test %d", index));
            product.setCreatedDate(Instant.ofEpochMilli(1338453298 + (index + 1)));
            product.setLastModifiedDate(Instant.ofEpochMilli(1338453298 + (index + 2)));

            products.add(product);
        });

        return products;
    }
}
