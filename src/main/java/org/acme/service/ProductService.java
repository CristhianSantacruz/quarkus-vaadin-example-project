package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.ProductModel;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService implements IProductService {

    @Override
    public String hello(String name){
        return "Hola como te va " + name;
    }

    @Override
    public Optional<ProductModel> productById(ObjectId idProduct) {
        return ProductModel.findByIdOptional(idProduct);
    }

    @Override
    public void saveProduct(ProductModel productModel) {
        productModel.persist();
    }

    @Override
    public boolean updateProduct(ObjectId id, ProductModel productModel) {
        Optional<ProductModel> productOptional = ProductModel.findByIdOptional(id);
        if (productOptional.isPresent()) {
            ProductModel productToUpdate = productOptional.get();
            productToUpdate.setName(productModel.getName());
            productToUpdate.setPrice(productModel.getPrice());
            productToUpdate.setDescription(productModel.getDescription());
            productToUpdate.setStock(productModel.getStock());
            productToUpdate.setImage(productModel.getImage());
            productToUpdate.update();
            return true;
        }
        return false;
    }

    @Override
    public List<ProductModel> getProductByPriceMore(double price) {
        return ProductModel.getProductsByPriceMore(price);
    }

    @Override
    public List<ProductModel> getProductByPriceLess(double price) {
        return ProductModel.getProductsByPriceLess(price);
    }

    @Override
    public List<ProductModel> allProduct() {
        return ProductModel.listAll();
    }

    @Override
    public boolean deleteProductById(ObjectId idProduct) {
        return ProductModel.deleteById(idProduct);
    }
}
