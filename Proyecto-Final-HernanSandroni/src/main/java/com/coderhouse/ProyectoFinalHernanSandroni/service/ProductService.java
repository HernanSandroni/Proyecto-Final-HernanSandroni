package com.coderhouse.ProyectoFinalHernanSandroni.service;


import com.coderhouse.ProyectoFinalHernanSandroni.model.Product;
import com.coderhouse.ProyectoFinalHernanSandroni.model.RequestProductDetail;
import com.coderhouse.ProyectoFinalHernanSandroni.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    // creo la logica para cada endpoint hecho en el controller
    public Product postProduct (Product product) throws Exception{
        return productRepository.save(product);
    }
    public Product getProduct (int id) throws Exception{
        Optional<Product> producto = productRepository.findById(id);
        if (producto.isEmpty()){
            throw new Exception("Product with id: " + id + ", not found");
        } else {
            return producto.get();
        }
    }

    public void updateProduct(Product product, int id) throws Exception {
        Optional<Product> productExistente = productRepository.findById(id);
        if(productExistente.isEmpty()){
            throw new Exception("Product not exist");
        } else {
            productExistente.get().setTitle(product.getTitle());
            productExistente.get().setDescription(product.getDescription());
            productExistente.get().setCode(product.getCode());
            productExistente.get().setPrice(product.getPrice());
            productExistente.get().setStock(product.getStock());
            productRepository.save(productExistente.get());
        }
    }

    public void deleteProduct (int id) throws Exception {
        Optional<Product> productoExistente = productRepository.findById(id);
        if(productoExistente.isEmpty()){
            throw new Exception("Product not exist");
        } else {
            productRepository.deleteById(id);
        }
    }

    public List<Product> getProducts() throws Exception{
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new Exception("Products not exist");
        }else {
            return productRepository.findAll();
        }
    }

    public List<Product> getProductsById(List<RequestProductDetail> productListId) throws Exception {
        List<Product> productList = new ArrayList<>();
        for (RequestProductDetail requestProduct:
                productListId) {
            Optional<Product> productFound = productRepository.findById(requestProduct.getProductId());
            if (productFound.isEmpty()){
                throw new Exception("Product with id: " + requestProduct.getProductId() + " not found.");
            }
            productList.add(productFound.get());
        }
        return productList;
    }

}