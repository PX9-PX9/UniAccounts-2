package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: ConcreteDecorator). Ajoute le restaurant universitaire. */
public class RestaurantService extends ServiceDecorator {
    public RestaurantService(AccountService wrapped) { super(wrapped); }
    @Override public String describe() { return wrapped.describe() + " + Restaurant"; }
    @Override public double monthlyFee() { return wrapped.monthlyFee() + 3000; }
}
