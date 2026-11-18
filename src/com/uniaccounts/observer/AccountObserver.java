package com.uniaccounts.observer;

/** Pattern: OBSERVER (role: Observer - interface). */
public interface AccountObserver {
    void update(AccountEvent event);
}
