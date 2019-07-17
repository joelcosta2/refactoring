package com.celfocus.training.view;

import com.celfocus.training.business.exception.BusinessException;
import com.celfocus.training.controller.IShopController;
import com.celfocus.training.controller.dtos.ProductDTO;
import com.celfocus.training.controller.dtos.ShopCartItemDTO;
import com.celfocus.training.controller.dtos.UserDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewShopping {

    private IShopController shopController;

    private static final Logger LOGGER = Logger.getLogger( "ViewShopping" );

    public ViewShopping(IShopController shopController) {
        this.shopController = shopController;
    }

    public void getUserShoppingCartList(UserDTO userDTO) {
        try {
            List<ShopCartItemDTO> shopCartItemDTOS = this.shopController.getUserShoppingCardList(userDTO);

            LOGGER.log(Level.FINE ,"Shopping Cart");
            LOGGER.log(Level.FINE ,"Username: " + userDTO.getUsername());
            LOGGER.log(Level.FINE ,"----------------");

            for (ShopCartItemDTO shopCartItemDTO : shopCartItemDTOS) {
                LOGGER.log(Level.FINE ,shopCartItemDTO.toString());
            }

            LOGGER.log(Level.FINE ,"----------------");

        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    public void addProductShoppingCart(ProductDTO productDTO, UserDTO userDTO) {
        try {
            shopController.addProductToUserShoppingCard(userDTO, productDTO);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }
}
