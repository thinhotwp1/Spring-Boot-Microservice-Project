package com.learn.productservice.service;

import com.learn.productservice.dto.ProductRequest;
import com.learn.productservice.dto.ProductResponse;
import com.learn.productservice.model.Product;
import com.learn.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository db;


    public void createProduct(ProductRequest request){
        Product product = Product.builder().
                name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        db.save(product);
        log.info("Product is save: {}",product);
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> list = db.findAll();
        List<ProductResponse> listRespone = list.stream().map(product -> mapToProductResponse(product)).toList();
        log.info("list product size: {}",listRespone.size());
        return listRespone;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
