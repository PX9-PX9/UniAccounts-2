package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: ConcreteDecorator). Ajoute l'acces a la bibliotheque. */
public class LibraryService extends ServiceDecorator {
    public LibraryService(AccountService wrapped) { super(wrapped); }
    @Override public String describe() { return wrapped.describe() + " + Bibliotheque"; }
    @Override public double monthlyFee() { return wrapped.monthlyFee() + 500; }
}
