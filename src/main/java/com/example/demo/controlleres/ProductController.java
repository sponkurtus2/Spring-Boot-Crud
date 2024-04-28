package com.example.demo.controlleres;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ProductsModel;
import com.example.demo.repositories.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductsModel> saveProduct(@RequestBody @Valid ProductRecordDto productrRecordDto) {
        var productModel = new ProductsModel();
        BeanUtils.copyProperties(productrRecordDto, productModel);   
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsModel>> getAllProducts() {
        List<ProductsModel> productList = productRepository.findAll();

        if (!productList.isEmpty()) {
            for(ProductsModel product : productList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModel> product0 = productRepository.findById(id);

        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found :(");
        }

        product0.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products list"));
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductsModel> product0 = productRepository.findById(id);

        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found :(");
        }

        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModel> product0 = productRepository.findById(id);

        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found :(");
        }

        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted ^^");
    }
    
    
}
