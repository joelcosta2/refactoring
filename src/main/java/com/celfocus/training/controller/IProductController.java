package com.celfocus.training.controller;

import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.controller.dtos.ProductDTO;

import java.util.List;

public interface IProductController {

    List<ProductDTO> getAllProductDTO();
    ProductDTO findUserByProductName(ProductDTO productDTOFromView);
    ProductDTO createProduct(ProductDTO productDTOFromView) throws SaveException;
    boolean deleteProduct(ProductDTO productDTOFromView) throws DeleteException;
    boolean updateProduct(ProductDTO productDTOFromView);
}
