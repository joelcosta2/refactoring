package com.celfocus.training.model;

public enum  DiscountType {
    BORN_BEFORE_80S(new Discount(1,0.2)),
    BORN_AFTER_80S(new Discount(2,0.1)),
    DEFAULT(new Discount(3,0.0));

    private Discount discount;

    @SuppressWarnings("unused")
    DiscountType(Discount discount){
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }
}
