package com.celfocus.training.model;

public class Discount extends AbstractModel {

    private double discountValue;

    public Discount(int id, double discount) {
        super(id);
        this.discountValue = discount;
    }

    public double getDiscountValue() {
        return discountValue;
    }
}
