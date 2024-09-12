package org.acme;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.acme.service.ProductService;
import com.vaadin.flow.component.dialog.Dialog;


public class ProductGrid extends Grid<ProductModel> {


    public ProductGrid(ProductService productService){

        super(ProductModel.class,false);
        addColumn(ProductModel::getName).setHeader("Nombre Producto");
        addColumn(ProductModel::getStock).setHeader("Stock");
        addColumn(ProductModel::getDescription).setHeader("Description");
        addColumn(ProductModel::getPrice).setHeader("Precio");
        addColumn(new ComponentRenderer<>(Button::new,(button , product)-> {
            Dialog dialog = new Dialog();
            dialog.getElement().setAttribute("aria-label", "Vista del Producto");
            Button closeButton = new Button(VaadinIcon.CLOSE.create());
            closeButton.addClickListener(e-> dialog.close());
            dialog.setDraggable(true);
                button.addClickListener(e->{
                    dialog.removeAll();
                    ProductCard productCard = new ProductCard(product);
                    dialog.add(closeButton);
                    dialog.add(productCard);
                    dialog.open();
                });
                Icon productIcon = new Icon(VaadinIcon.PACKAGE);
                button.setIcon(productIcon);

        })).setHeader("Ver Producto");
        addColumn(
                new ComponentRenderer<>(Button::new,(button, product)->{
                    Dialog dialogUpdateProduct = new Dialog();
                    dialogUpdateProduct.getElement().setAttribute("aria-label", "Ver Producto");
                    button.addClickListener(e->{
                        VerticalLayout dialogLayout = createDialogUpdateProduct(dialogUpdateProduct,product,productService);
                        dialogUpdateProduct.add(dialogLayout);
                        dialogUpdateProduct.open();

                    });
                    Icon iconUpdate = new Icon(VaadinIcon.TOOLS);
                    iconUpdate.setColor("blue");
                    button.setIcon(iconUpdate);
                })).setHeader("Actualizar");

        addColumn(
                new ComponentRenderer<>(Button::new,(button , product)->{
                    button.addClickListener(buttonClickEvent -> {

                        ConfirmDialog dialogDelete = new ConfirmDialog();
                        dialogDelete.setHeader("Eliminar Producto");
                        dialogDelete.setText("Estas seguro de eliminar este producto?");
                        dialogDelete.setCancelable(true);
                        dialogDelete.setConfirmText("Eliminar");
                        dialogDelete.setCancelText("Cancelar");
                        dialogDelete.addConfirmListener(confirmEvent -> {
                            productService.deleteProductById(product.id);
                           // MainView.updateProductCards("","");
                            setItems(productService.allProduct());
                        });

                        dialogDelete.open();
                    });
                    Icon iconTrash = new Icon(VaadinIcon.TRASH);
                    iconTrash.setColor("red");
                    button.setIcon(iconTrash);
                })
        ).setHeader("Eliminar");
        setItems(productService.allProduct());
    }

    private static VerticalLayout createDialogUpdateProduct(Dialog dialog,ProductModel product,ProductService productService){
        H2 headline = new H2("Actualizar Producto");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        VerticalLayout fieldLayout = new VerticalLayout();
        TextField nameProductField = new TextField("Nombre del Producto");
        nameProductField.setValue(product.getName());
        TextField imageProductField = new TextField("Imagen del Producto");
        imageProductField.setValue(product.getImage());
        TextField stockProductField = new TextField("Stock del producto");
        stockProductField.setValue(String.valueOf(product.getStock()));
        TextArea descriptionProductField = new TextArea("Descripcion");
        descriptionProductField.setValue(product.getDescription());
        TextField priceProductField = new TextField("Precio");
        priceProductField.setValue(String.valueOf(product.getPrice()));
        fieldLayout.add(headline);
        fieldLayout.add(nameProductField);
        fieldLayout.add(imageProductField);
        fieldLayout.add(priceProductField,stockProductField,descriptionProductField);



        Button cancelButton = new Button("Cancel", e -> dialog.close());

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e->{
            ProductModel newProductModel = new ProductModel();
            newProductModel.setName(nameProductField.getValue());
            newProductModel.setImage(imageProductField.getValue());
            newProductModel.setStock(Integer.parseInt(stockProductField.getValue()));
            newProductModel.setDescription(descriptionProductField.getValue());
            newProductModel.setPrice(Double.parseDouble(priceProductField.getValue()));
            boolean result = productService.updateProduct(product.id,newProductModel);
            if(result){
                dialog.close();
            }
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }
}
