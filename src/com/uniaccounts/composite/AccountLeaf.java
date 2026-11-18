package com.uniaccounts.composite;

import com.uniaccounts.state.AccountContext;

/** Pattern: COMPOSITE (role: Leaf). Un compte etudiant : il compte pour 1. */
public class AccountLeaf extends OrgComponent {
    private final AccountContext account;

    public AccountLeaf(AccountContext account) { this.account = account; }

    @Override public String getName() { return account.getAccount().getFullName(); }
    @Override public int countAccounts() { return 1; }
    @Override public void print(String indent) {
        System.out.println(indent + "- " + getName() + " [" + account.getStatus() + "]");
    }
}
