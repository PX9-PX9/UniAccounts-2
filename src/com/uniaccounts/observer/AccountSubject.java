package com.uniaccounts.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern: OBSERVER (role: Subject).
 * Garde la liste des observateurs et les previent tous quand un evenement arrive.
 * Ajouter un nouvel observateur ne demande AUCUNE modification des comptes.
 */
public class AccountSubject {
    private final List<AccountObserver> observers = new ArrayList<>();

    public void attach(AccountObserver o) { observers.add(o); }
    public void detach(AccountObserver o) { observers.remove(o); }

    public void notifyObservers(AccountEvent event) {
        for (AccountObserver o : observers) o.update(event);
    }
}
