package com.uniaccounts.state;

/**
 * Pattern: STATE (role: State - interface).
 * Chaque etat decide quelles operations sont permises et vers quel etat on passe.
 * Par defaut toute operation est INTERDITE ; chaque etat concret ne redefinit que ce qu'il autorise.
 */
public interface AccountState {
    AccountStatus status();

    default void activate(AccountContext ctx) { throw interdit("activate"); }
    default void suspend(AccountContext ctx)  { throw interdit("suspend"); }
    default void close(AccountContext ctx)    { throw interdit("close"); }
    /** Par defaut la connexion est refusee ; seul l'etat ACTIVE l'autorise. */
    default boolean login(AccountContext ctx) { return false; }

    private IllegalStateException interdit(String op) {
        return new IllegalStateException("Operation '" + op + "' interdite : le compte est " + status());
    }
}
