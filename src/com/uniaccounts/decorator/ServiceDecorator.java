package com.uniaccounts.decorator;

/** Pattern: DECORATOR (role: Decorator). Meme interface que le composant + une reference vers le composant enveloppe. */
public abstract class ServiceDecorator implements AccountService {
    protected final AccountService wrapped;

    protected ServiceDecorator(AccountService wrapped) { this.wrapped = wrapped; }
}
