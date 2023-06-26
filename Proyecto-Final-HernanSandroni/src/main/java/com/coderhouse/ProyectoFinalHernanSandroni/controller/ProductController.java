package com.coderhouse.ProyectoFinalHernanSandroni.controller;


import com.coderhouse.ProyectoFinalHernanSandroni.middleware.ResponseHandler;
import com.coderhouse.ProyectoFinalHernanSandroni.model.Product;
import com.coderhouse.ProyectoFinalHernanSandroni.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/v1/product")

public class ProductController {

    @Autowired
    private ProductService productService;

    // creo un producto
    @PostMapping
    public ResponseEntity<Object> postProduct (@RequestBody Product product){
        try{
            this.isValid(product);
            System.out.println(product); // metodo de validacion
            Product productSaved = productService.postProduct(product);
            return ResponseHandler.generateResponse(
                    "Product stores successfully",
                    HttpStatus.OK,
                    productSaved
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    //Creo un Get para buscar un producto por id
    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getProduct (@PathVariable() int id){
        try{
            System.out.println(id);
            Product productFound = productService.getProduct(id);
            return ResponseHandler.generateResponse(
                    "Product retrieved successfully",
                    HttpStatus.OK,
                    productFound
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    //creo un Put para actualizar a un producto
    @PutMapping(path = "{product_id}")
    public ResponseEntity<Object> putProduct (
            @PathVariable("product_id") int id,
            @RequestBody Product product
    ) {
        try {
            System.out.println(product);
            System.out.println(id);
            productService.updateProduct(product, id);
            return ResponseHandler.generateResponse(
                    "Data updated successfully",
                    HttpStatus.OK,
                    id
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }


    // creo un Delete para borrar a un producto
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable() int id) {
        try {
            System.out.println(id);
            productService.deleteProduct(id);
            return ResponseHandler.generateResponse(
                    "Product deleted successfully",
                    HttpStatus.OK,
                    id
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    //creo un get para buscar un listado de productos
    @GetMapping(path = "/products")
    public ResponseEntity<Object> getProducts (){
        try{
            List<Product> products = productService.getProducts();
            return ResponseHandler.generateResponse(
                    "Products retrieved successfully",
                    HttpStatus.OK,
                    products
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
    // creo un metodo para validar si los campos vienen vacios
    private void isValid(Product product) throws Exception {
        if (Objects.isNull(product.getTitle()) && product.getTitle().isBlank()){
            throw new Exception("Product title must not be empty");
        } else if (Objects.isNull(product.getDescription()) && product.getDescription().isBlank()){
            throw new Exception("Product description must not be empty");
        } else if (Objects.isNull(product.getCode()) && product.getCode().isBlank()){
            throw new Exception("Product code must not be empty");
        } else if (Objects.isNull(product.getPrice())) {
            throw new Exception("Product price must not be empty");
        } else if (Objects.isNull(product.getStock())) {
            throw new Exception("Product stock must not be empty");
        }
    }
}
