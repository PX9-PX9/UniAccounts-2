package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: ConcreteComponent). Le service de base, que l'on va "decorer". */
public class BasicAccountService implements AccountService {
    @Override public String describe() { return "Compte de base"; }
    @Override public double monthlyFee() { return 0; }
}
