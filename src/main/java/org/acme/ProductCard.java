package org.acme;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//@CssImport("./styles/styles.css")
public class ProductCard extends Div {

    public ProductCard(ProductModel productModel) {


        Image productImage = new Image(productModel.getImage(),"Imagen Producto");
        productImage.addClassName("product-image");

        Span nameSpan = new Span(productModel.getName());
        nameSpan.addClassName("product-name");

        Span descriptionSpan = new Span(productModel.getDescription());
        descriptionSpan.addClassName("product-description");

        Span priceSpan = new Span(String.valueOf(productModel.getPrice()));
        priceSpan.addClassName("product-price");

        Span stockSpan = new Span("Stock:"+ String.valueOf(productModel.getStock()));
        stockSpan.addClassName("product-stock");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        priceSpan.add(new Icon(VaadinIcon.DOLLAR));
        horizontalLayout.add(priceSpan);
        horizontalLayout.add(stockSpan);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        horizontalLayout.addClassName("horizontal-layout");

        addClassName("product-card");
        add(productImage, nameSpan, descriptionSpan, horizontalLayout);
    }
}
