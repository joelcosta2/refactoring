package com.celfocus.training.business.impl;


import com.celfocus.training.business.IProductBusiness;
import com.celfocus.training.business.IShopBusiness;
import com.celfocus.training.business.IUserBusiness;
import com.celfocus.training.business.exception.BusinessException;
import com.celfocus.training.model.Discount;
import com.celfocus.training.model.DiscountType;
import com.celfocus.training.model.Product;
import com.celfocus.training.model.ShopCartItem;
import com.celfocus.training.model.User;
import com.celfocus.training.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.celfocus.training.util.constant.ConstantNumbers.*;

/*
    -shop add product in user list
    -shop create shoppingCard for user
    -shop apply the discountType on product
    -shop remove product in shoppingCard from user list
*/
public class ShopBusinessImp implements IShopBusiness {

    private IUserBusiness userBusiness;
    private IProductBusiness productBusiness;
    private Map<User, List<ShopCartItem>> shoppingCartUser;

    public ShopBusinessImp(IUserBusiness userBusiness, IProductBusiness productBusiness) {
        this.shoppingCartUser = new HashMap();
        this.userBusiness = userBusiness;
        this.productBusiness = productBusiness;
    }

    @Override
    public List<ShopCartItem> addProductToUserShoppingCard(Product product, User user) {

        this.logExceptionsCase(product, user);

        List<ShopCartItem> userShoppingCartItemList = this.getUserShoppingCardList(user);

        if (userShoppingCardListHaveProduct(userShoppingCartItemList, product)) {
            List<ShopCartItem> shopCartItemList = increaseProductQuantity(product, userShoppingCartItemList);
            this.shoppingCartUser.replace(user, shopCartItemList);
            return shopCartItemList;
        }

        int nextId = START_ID;

        if (userShoppingCartItemList != null) {
            nextId = userShoppingCartItemList.size() + 1;
        }

        Discount discount = this.getDiscountForUser(user);

        ShopCartItem shopCartItem = new ShopCartItem(nextId, product, discount, START_QUANTITY);

        if (userShoppingCartItemList != null) {
            userShoppingCartItemList.add(shopCartItem);
        }

        this.shoppingCartUser.replace(user, userShoppingCartItemList);

        return userShoppingCartItemList;
    }

    @Override
    public List<ShopCartItem> removeProductToUserShoppingCard(Product product, User user) {
        List<ShopCartItem> userShoppingCardList = this.getUserShoppingCardList(user);

        ShopCartItem shopCartItemToRemove = null;

        for (ShopCartItem shopCartItem : userShoppingCardList) {
            if (shopCartItem.getProduct().getProductName().equals(product.getProductName())) {
                shopCartItemToRemove = shopCartItem;
            }
        }

        if (shopCartItemToRemove == null) {
            return userShoppingCardList;
        }

        userShoppingCardList.remove(shopCartItemToRemove);

        this.shoppingCartUser.replace(user, userShoppingCardList);

        return userShoppingCardList;
    }

    @Override
    public List<ShopCartItem> getUserShoppingCardList(User user) {

        if (!userHaveShoppingCard(user)) {
            return this.createShoppingCard(user);
        }

        return this.getUserShoppingCards(user);
    }

    private void logExceptionsCase(Product product, User user) {
        try {
            if (!this.userBusiness.existingUser(user)) {
                throw new BusinessException("User not found.");
            }

            if (!this.productBusiness.existingProduct(product)) {
                throw new BusinessException("Product not found.");
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    private boolean userShoppingCardListHaveProduct(List<ShopCartItem> userShoppingCardList, Product product) {
        if (userShoppingCardList == null) {
            return false;
        }

        return userShoppingCardList.stream()
                .anyMatch(shopCartItem -> shopCartItem.getProduct().getProductName().equals(product.getProductName()));
    }

    private List<ShopCartItem> increaseProductQuantity(Product product, List<ShopCartItem> userShoppingCardList) {
        for (ShopCartItem shopCartItem : userShoppingCardList) {
            if (shopCartItem.getProduct().getProductName().equals(product.getProductName())) {
                int currentQuantity = shopCartItem.getQuantity();
                shopCartItem.setQuantity(currentQuantity + 1);
                break;
            }
        }
        return userShoppingCardList;
    }

    private List<ShopCartItem> createShoppingCard(User user) {
        List<ShopCartItem> userShoppingList = new ArrayList<>();

        this.shoppingCartUser.put(user, userShoppingList);

        return userShoppingList;
    }

    private List<ShopCartItem> getUserShoppingCards(User user) {
        return this.shoppingCartUser.get(user);
    }

    private Discount getDiscountForUser(User user) {
        boolean isMajor = user.isMajor();
        boolean less80YearOld = Utils.getAgeFromDate(user.getBirthDate()) < EIGHTY_YEAROLD;

        if (isMajor && less80YearOld) {
            return DiscountType.BORN_BEFORE_80S.getDiscount();
        }

        if (isMajor) {
            return DiscountType.BORN_AFTER_80S.getDiscount();
        }

        return DiscountType.DEFAULT.getDiscount();

    }

    private boolean userHaveShoppingCard(User user) {
        
        return this.shoppingCartUser.containsKey(user);
    }

}
