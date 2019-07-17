package com.celfocus.training.view;

import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.controller.IProductController;
import com.celfocus.training.controller.dtos.ProductDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewProduct {

    public static final String SPAN = "<span>";
    IProductController productController;

    private static final Logger LOGGER = Logger.getLogger( "ViewProduct" );

    public ViewProduct(IProductController productController) {
        this.productController = productController;
    }

    public void saveProduct(ProductDTO productDTO) {
        try {

            this.productController.createProduct(productDTO);

        } catch (SaveException e) {
            e.printStackTrace();
        }
    }

    public void printProducts(){
        List<ProductDTO> allProductDTO = productController.getAllProductDTO();

        LOGGER.log(Level.FINE,"All Products");
        LOGGER.log(Level.FINE,"----------------");

        for (ProductDTO productDTO : allProductDTO) {
            LOGGER.log(Level.FINE ,productDTO.toString());
        }

        LOGGER.log(Level.FINE ,"----------------");

    }

    private String buildFileProductXml(ProductDTO productDTO) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>")
                .append("<productName>").append(productDTO.getProductName()).append("<productName>")
                .append("<amount>").append(productDTO.getAmount()).append("<amount>");


        return stringBuilder.toString();
    }

    private String buildFileProductHtml(ProductDTO productDTO) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<div>")
                .append("<h1>Product</h1>")
                .append(SPAN).append(productDTO.getProductName()).append(SPAN)
                .append(SPAN).append(productDTO.getAmount()).append(SPAN)
                .append("</div>");

        return stringBuilder.toString();
    }

    public void deleteUser(ProductDTO productDTO) {
        try {
            productController.deleteProduct(productDTO);
        } catch (DeleteException e) {
            e.printStackTrace();
        }
    }

}
