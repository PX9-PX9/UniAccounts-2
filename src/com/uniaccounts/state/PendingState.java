package com.uniaccounts.state;

import com.uniaccounts.observer.EventType;

/** Pattern: STATE (role: ConcreteState). Compte neuf : il attend l'activation. */
public class PendingState implements AccountState {
    @Override public AccountStatus status() { return AccountStatus.PENDING; }

    @Override public void activate(AccountContext ctx) {
        ctx.setState(new ActiveState());
        ctx.announce(EventType.ACTIVATED);
    }
}
