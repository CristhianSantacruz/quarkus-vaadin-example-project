package org.acme;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.pro.licensechecker.Product;
import jakarta.inject.Inject;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.acme.service.IProductService;
import org.acme.service.ProductService;
import javax.swing.*;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout {
    public MainView(ProductService iproductService) {

        Span titleSpan = new Span("Proyecto FullStack Vaadin & Quarkus");
        // Use TextField for standard text input
        titleSpan.getStyle().set("font-size","30px");
        titleSpan.getStyle().set("font-style","italic");
        titleSpan.getStyle().set("text-align","center");
        titleSpan.getStyle().set("font-weight","bold");
        titleSpan.getStyle().set("color","skye");
        TextField textField = new TextField("Ingr esa tu nombre");
        textField.addThemeName("bordered");

        Button button2 = new Button("Saludar New",new Icon(VaadinIcon.USER),e->{
            add(new Paragraph(iproductService.hello(textField.getValue())));
        });
        button2.addClassName("button-style");
        button2.addClickShortcut(Key.ENTER);

        HorizontalLayout layout1 = new HorizontalLayout();
        layout1.add(textField);
        layout1.add(button2);
        layout1.setAlignSelf(FlexComponent.Alignment.END, button2);

        TextField productName = new TextField("Ingresa el nombre del product");
        TextField productDescription = new TextField("Ingresa el descripcion del product");
        TextField productPrice = new TextField("Ingresa el precio del product");
        TextField productStock = new TextField("Ingresa el stock del product");
        TextField productImage = new TextField("Ingresa el image del product");

        FormLayout formLayout = new FormLayout();
        formLayout.add(productName,productDescription,productPrice,productStock,productImage);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        formLayout.setColspan(productImage,2);


        Grid<ProductModel> gridProducts = new Grid<>(ProductModel.class,false);

        gridProducts.addColumn(ProductModel::getName).setHeader("Nombre Producto");
        //gridProducts.addColumn(ProductModel::getDescription).setHeader("Descripcion");
        gridProducts.addColumn(ProductModel::getStock).setHeader("Stock");
        gridProducts.addColumn(ProductModel::getImage).setHeader("Imagen");
        gridProducts.addColumn(ProductModel::getPrice).setHeader("Precio");

        gridProducts.setItems(iproductService.allProduct());

        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.setWidthFull();
        layout2.setJustifyContentMode(JustifyContentMode.CENTER);


        Button buttonSaveProduct = new Button("Guardar Producto",new Icon(VaadinIcon.SHOP),e->{
            ProductModel productModel = new ProductModel();
            productModel.setName(productName.getValue());
            productModel.setPrice(Double.parseDouble(productPrice.getValue()));
            productModel.setDescription(productDescription.getValue());
            productModel.setStock(Integer.parseInt(productStock.getValue()));
            productModel.setImage(productImage.getValue());
            iproductService.saveProduct(productModel);
            gridProducts.setItems(iproductService.allProduct());
            productName.clear();
            productPrice.clear();
            productDescription.clear();
            productStock.clear();
            productImage.clear();
        });
        layout2.add(buttonSaveProduct);
        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.

        HorizontalLayout layoutProductCards = new HorizontalLayout();
        layoutProductCards.setAlignItems(Alignment.CENTER);

        for(ProductModel productModel : iproductService.allProduct()){
            ProductCard productCard = new ProductCard(productModel);
            layoutProductCards.add(productCard);
        }


        addClassName("centered-content");
        addClassName("button-style");
        add(titleSpan,layout1,formLayout,layout2,layoutProductCards,new Span("Tabla de Productos"),gridProducts);
    }
}
