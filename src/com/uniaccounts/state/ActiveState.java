package com.uniaccounts.state;

import com.uniaccounts.observer.EventType;

/** Pattern: STATE (role: ConcreteState). Compte actif : connexion permise. */
public class ActiveState implements AccountState {
    @Override public AccountStatus status() { return AccountStatus.ACTIVE; }

    @Override public void suspend(AccountContext ctx) {
        ctx.setState(new SuspendedState());
        ctx.announce(EventType.SUSPENDED);
    }
    @Override public void close(AccountContext ctx) {
        ctx.setState(new ClosedState());
        ctx.announce(EventType.CLOSED);
    }
    @Override public boolean login(AccountContext ctx) { return true; }
}
