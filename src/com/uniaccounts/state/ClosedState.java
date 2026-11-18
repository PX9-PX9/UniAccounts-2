package com.uniaccounts.state;

/** Pattern: STATE (role: ConcreteState). Etat final (diplome) : plus aucune operation n'est permise. */
public class ClosedState implements AccountState {
    @Override public AccountStatus status() { return AccountStatus.CLOSED; }
}
