package com.celfocus.training.business;

import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.model.Product;

import java.util.List;

public interface IProductBusiness {

    List<Product> getAllProducts();

    Product findProductByName(String productName);

    void saveProduct(Product product) throws SaveException;

    boolean updateProduct(Product userWithChange);

    boolean deleteProduct(Product product) throws DeleteException;

    boolean existingProduct(Product product);

}
