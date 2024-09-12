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
import com.vaadin.flow.data.renderer.ComponentRenderer;
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

        Span titlePage = new Span("Proyecto FullStack Vaadin & Quarkus");
        titlePage.addClassName("title-span");

        TextField productName = new TextField("Ingresa el nombre del product");
        TextField productDescription = new TextField("Ingresa el descripcion del product");
        TextField productPrice = new TextField("Ingresa el precio del product");
        TextField productStock = new TextField("Ingresa el stock del product");
        TextField productImage = new TextField("Ingresa el image del product");

        Span titleForm = new Span("Registrar Producto");
        titleForm.addClassName("title-span");

        FormLayout formLayout = new FormLayout();
        formLayout.add(productName,productDescription,productPrice,productStock,productImage);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        formLayout.setColspan(productImage,2);


        Span titleGrid = new Span("Tabla de Productos");
        titleGrid.addClassName("title-span");

        ProductGrid gridProducts = new ProductGrid(iproductService);

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
        addClassName("centered-content");
        add(titlePage,titleForm,formLayout,layout2,titleGrid,gridProducts);

    }

}
