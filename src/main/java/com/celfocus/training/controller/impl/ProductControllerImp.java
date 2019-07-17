package com.celfocus.training.controller.impl;

import com.celfocus.training.business.IProductBusiness;
import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.controller.IProductController;
import com.celfocus.training.controller.dtos.ProductDTO;
import com.celfocus.training.model.Product;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static com.celfocus.training.util.constant.ConstantNumbers.*;

public class ProductControllerImp implements IProductController {

    private IProductBusiness productBusiness;

    public ProductControllerImp(IProductBusiness productBusiness) {
        this.productBusiness = productBusiness;
    }

    @Override
    public List<ProductDTO> getAllProductDTO() {
        List<Product> productList = productBusiness.getAllProducts();

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product: productList){
            ProductDTO productDTO = new ProductDTO(product.getId(),product.getProductName(),product.getAmount());
            productDTOList.add(productDTO);
        }

        return productDTOList;

    }

    @Override
    public ProductDTO findUserByProductName(ProductDTO productDTOFromView) {
        ProductDTO productDTO = new ProductDTO(ID_TO_DEFAULT);

        if (isProductDTOFromViewNull(productDTOFromView)) return productDTO;

        Product product = this.productBusiness.findProductByName(productDTOFromView.getProductName());

        if (product == null) {
            return productDTO;
        }

        BeanUtils.copyProperties(product,productDTO);

        return productDTO;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTOFromView) throws SaveException {
        ProductDTO productDTO = new ProductDTO(ID_TO_DEFAULT);

        if (isProductDTOFromViewNull(productDTOFromView)) return productDTO;

        Product product = this.convertProductDtoToProduct(productDTOFromView);

        this.productBusiness.saveProduct(product);

        return productDTOFromView;
    }

    @Override
    public boolean deleteProduct(ProductDTO productDTOFromView) throws DeleteException {
        if (isProductDTOFromViewNull(productDTOFromView)) return false;

        Product product = this.convertProductDtoToProduct(productDTOFromView);

        return this.productBusiness.deleteProduct(product);
    }

    @Override
    public boolean updateProduct(ProductDTO productDTOFromView) {
        if (isProductDTOFromViewNull(productDTOFromView)) return false;

        Product product = this.convertProductDtoToProduct(productDTOFromView);

        return this.productBusiness.updateProduct(product);
    }

    private boolean isProductDTOFromViewNull(ProductDTO productDTOFromView) {
        return productDTOFromView == null || productDTOFromView.getProductName() == null;
    }

    private Product convertProductDtoToProduct(ProductDTO productDTOFromView) {
        Product product = new Product(ID_TO_DEFAULT);

        BeanUtils.copyProperties(productDTOFromView,product);
        return product;
    }
}
