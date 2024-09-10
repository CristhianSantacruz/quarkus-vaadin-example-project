package org.acme.service;

import org.acme.ProductModel;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Optional<ProductModel> productById(ObjectId idProduct);
    void saveProduct(ProductModel productModel);
    boolean updateProduct(ObjectId id , ProductModel productModel);
    List<ProductModel> getProductByPriceMore(double price);
    List<ProductModel> getProductByPriceLess(double price);
    List<ProductModel> allProduct();
    boolean deleteProductById(ObjectId idProduct);
    String hello(String name);
}
