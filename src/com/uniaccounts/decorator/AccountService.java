package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: Component). Les services dont beneficie un etudiant. */
public interface AccountService {
    String describe();
    double monthlyFee();   // montant mensuel en DA (valeurs fictives pour la demonstration)
}
