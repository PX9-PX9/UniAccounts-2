package com.uniaccounts.composite;

/**
 * Pattern: COMPOSITE (role: Component).
 * Interface commune a une feuille (un compte) et a un noeud (universite, faculte, departement, groupe).
 * Le client pose la meme question "combien de comptes ?" a n'importe quel noeud.
 */
public abstract class OrgComponent {
    public abstract String getName();
    public abstract int countAccounts();
    public abstract void print(String indent);
}
