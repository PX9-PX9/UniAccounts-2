package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: ConcreteDecorator). Ajoute l'acces Internet. */
public class InternetService extends ServiceDecorator {
    public InternetService(AccountService wrapped) { super(wrapped); }
    @Override public String describe() { return wrapped.describe() + " + Internet"; }
    @Override public double monthlyFee() { return wrapped.monthlyFee() + 1000; }
}
