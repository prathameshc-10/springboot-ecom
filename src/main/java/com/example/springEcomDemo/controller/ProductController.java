package com.example.springEcomDemo.controller;

import com.example.springEcomDemo.model.Product;
import com.example.springEcomDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

//    @GetMapping("/product/{id}")
//    public ResponseEntity<Product> getProduct(@PathVariable int id) {
//        return productService.getProduct(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = productService.getProduct(id);
        if(product.getId() > 0){
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else{
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id){
        Product product = productService.getProduct(id);
        if(product.getId()>0){
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile image){
        Product savedProduct = null;

        try {
            savedProduct = productService.addOrUpdateProduct(product, image);
            return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            product.setId(id);
            productService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = productService.getProduct(id);
        if(product.getId() > 0){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products = productService.searchProduct(keyword);
        System.out.println("Searching with: " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
