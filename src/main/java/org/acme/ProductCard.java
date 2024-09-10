package org.acme;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ProductCard extends VerticalLayout {

    public ProductCard(ProductModel productModel) {
        Image productImage = new Image(productModel.getImage(),"Imagen Producto");
        productImage.setWidth("150px");
        productImage.setHeight("150px");

        Span nameSpan = new Span(productModel.getName());
        nameSpan.getStyle().set("font-size","20px");
        nameSpan.getStyle().set("margin-bottom","-10px");
        Span descriptionSpan = new Span(productModel.getDescription());
        descriptionSpan.getStyle().set("font-size", "14px");
        descriptionSpan.getStyle().set("word-wrap", "break-word");
        Span priceSpan = new Span(String.valueOf(productModel.getPrice()));
        Span stockSpan = new Span("Stock:"+ String.valueOf(productModel.getStock()));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        priceSpan.add(new Icon(VaadinIcon.DOLLAR));
        horizontalLayout.add(priceSpan);
        horizontalLayout.add(stockSpan);
        horizontalLayout.setWidthFull();

        this.setWidth("500px");
        this.setHeight("350px");
        this.getStyle().set("border", "1px solid #ccc");
        this.getStyle().set("padding", "10px");
        this.getStyle().set("border-radius", "8px");
        this.getStyle().set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)");

        add(productImage, nameSpan,descriptionSpan ,horizontalLayout);
    }
}
