package com.uniaccounts.state;

import com.uniaccounts.observer.EventType;

/** Pattern: STATE (role: ConcreteState). Compte suspendu : pas de connexion, mais on peut le reactiver ou le fermer. */
public class SuspendedState implements AccountState {
    @Override public AccountStatus status() { return AccountStatus.SUSPENDED; }

    @Override public void activate(AccountContext ctx) {   // reactivation
        ctx.setState(new ActiveState());
        ctx.announce(EventType.ACTIVATED);
    }
    @Override public void close(AccountContext ctx) {
        ctx.setState(new ClosedState());
        ctx.announce(EventType.CLOSED);
    }
}
