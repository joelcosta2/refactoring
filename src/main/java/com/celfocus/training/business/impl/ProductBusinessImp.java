package com.celfocus.training.business.impl;

import com.celfocus.training.business.IProductBusiness;
import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.model.Product;

import java.util.List;

public class ProductBusinessImp extends AbstractOperations<Product> implements IProductBusiness {

    @Override
    public List<Product> getAllProducts() {
        return super.getAll();
    }

    @Override
    public Product findProductByName(String productName) {

        for (Product product : super.getAll()) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }

        return null;
    }

    @Override
    public void saveProduct(Product product) throws SaveException {
        if (existingProduct(product)) throw new SaveException("This product already exist.");

        product.setId(super.getNextIndex());

        super.save(product);
    }

    @Override
    public boolean updateProduct(Product productWithChange){
        Product productFound = this.findProductByName(productWithChange.getProductName());
        int productIndex = super.getIndex(productFound);

        super.update(productIndex, productWithChange);

        return true;
    }

    @Override
    public boolean deleteProduct(Product product) throws DeleteException {
        if (!existingProduct(product)) throw new DeleteException("User don´t exist.");

        int productIndex = super.getIndex(product);

        super.delete(productIndex);

        return true;
    }

    @Override
    public boolean existingProduct(Product product) {
        return super.getAll().contains(product) || existingProductName(product.getProductName());
    }

    private boolean existingProductName(String productName) {
        if (productName == null) {
            return false;
        }

        return super.getAll().stream()
                .anyMatch(user -> user.getProductName().equals(productName));
    }

}
