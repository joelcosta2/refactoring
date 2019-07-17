package com.celfocus.training;

import com.celfocus.training.view.ViewProduct;
import com.celfocus.training.view.ViewShopping;
import com.celfocus.training.view.ViewUser;
import com.celfocus.training.business.IProductBusiness;
import com.celfocus.training.business.IShopBusiness;
import com.celfocus.training.business.IUserBusiness;
import com.celfocus.training.business.impl.ProductBusinessImp;
import com.celfocus.training.business.impl.ShopBusinessImp;
import com.celfocus.training.business.impl.UserBusinessImp;
import com.celfocus.training.controller.IProductController;
import com.celfocus.training.controller.IShopController;
import com.celfocus.training.controller.IUserController;
import com.celfocus.training.controller.dtos.ProductDTO;
import com.celfocus.training.controller.dtos.UserDTO;
import com.celfocus.training.controller.impl.ProductControllerImp;
import com.celfocus.training.controller.impl.ShopControllerImp;
import com.celfocus.training.controller.impl.UserControllerImp;


public class Main {

    public static void main(String[] args) {

        // Wire App logic
        IUserBusiness userBusiness = new UserBusinessImp();
        IProductBusiness productBusiness = new ProductBusinessImp();
        IShopBusiness shopBusiness = new ShopBusinessImp(userBusiness,productBusiness);

        // Wire Controllers
        IProductController productController = new ProductControllerImp(productBusiness);
        IUserController userController = new UserControllerImp(userBusiness);
        IShopController shopController = new ShopControllerImp(shopBusiness);

        // Wire view
        ViewProduct viewProduct = new ViewProduct(productController);
        ViewUser viewUser = new ViewUser(userController);
        ViewShopping viewShopping = new ViewShopping(shopController);

        // Creating the products
        ProductDTO douroTravel = new ProductDTO("Douro Travel in boat.", 59.99);
        ProductDTO clerigosTower = new ProductDTO("Clérigos tower visit.", 12.00);

        viewProduct.saveProduct(douroTravel);
        viewProduct.saveProduct(clerigosTower);

        // create users
        viewUser.createUser("João Sousa", "1960","04", "24");
        viewUser.createUser("Maria Albertina", "1955","04", "24");

        // view products
        viewProduct.printProducts();

        // view users
        viewUser.printUsers();

        UserDTO joaoSousa = viewUser.getUserDTO("João Sousa");

        viewShopping.addProductShoppingCart(douroTravel, joaoSousa);

        viewShopping.getUserShoppingCartList(joaoSousa);


    }

}
