package org.acme;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.acme.service.ProductService;

public class ProductGrid extends Grid<ProductModel> {

    public ProductGrid(ProductService productService){
        super(ProductModel.class,false);
        addColumn(ProductModel::getName).setHeader("Nombre Producto");
        addColumn(ProductModel::getStock).setHeader("Stock");
        addColumn(ProductModel::getDescription).setHeader("Description");
        addColumn(ProductModel::getPrice).setHeader("Precio");
        addColumn(
                new ComponentRenderer<>(Button::new,(button, product)->{
                    button.addClickListener(e -> {

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
}
