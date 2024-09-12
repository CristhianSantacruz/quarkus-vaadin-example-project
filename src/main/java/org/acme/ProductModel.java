package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "product")
public class ProductModel extends PanacheMongoEntity {

    private String name;
    private String description;
    private double price;
    private int stock;
    private String image;




    public static List<ProductModel> getProductsByPriceMore(double price) {
        return ProductModel.find("price >= ?1",price).list();
    }

    public static List<ProductModel> getProductsByPriceLess(double price) {
        return ProductModel.find("price <= ?1",price).list();
    }

    public static List<ProductModel> searchProductByName(String name){
        String filter = "(?i).*" + name + ".*";
        return ProductModel.find("name ilike ?1", filter).list();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
