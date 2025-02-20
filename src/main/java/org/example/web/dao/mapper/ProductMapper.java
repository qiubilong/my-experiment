package org.example.web.dao.mapper;

import org.example.web.dao.entity.Product;
import org.springframework.stereotype.Component;

/**
 * @author chenxuegui
 * @since 2025/2/20
 */
@Component
public class ProductMapper {

    public Product getProduct(Long id){
        if(id<1000){
            return null;
        }

        Product product = new Product();
        product.setId(id);
        return product;
    }

    public int updateProduct(Product product){
        return 1;
    }

    public int createProduct(Product product){
        return 1;
    }
}
