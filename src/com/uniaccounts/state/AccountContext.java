package com.uniaccounts.state;

import com.uniaccounts.decorator.AccountService;
import com.uniaccounts.decorator.BasicAccountService;
import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.observer.AccountEvent;
import com.uniaccounts.observer.AccountSubject;
import com.uniaccounts.observer.EventType;

/**
 * Pattern: STATE (role: Context).
 * Enveloppe un StudentAccount (Travail 1, inchange et immuable) et lui ajoute un etat qui change.
 * On n'a donc PAS modifie StudentAccount : on le compose.
 * Il joue aussi le role d'emetteur des evenements vers le Subject du pattern OBSERVER.
 */
public class AccountContext {
    private final StudentAccount account;
    private final AccountSubject subject;
    private AccountState state = new PendingState();            // tout compte commence en PENDING
    private AccountService services = new BasicAccountService(); // pattern DECORATOR (services supplementaires)

    public AccountContext(StudentAccount account, AccountSubject subject) {
        this.account = account;
        this.subject = subject;
    }

    // --- operations deleguees a l'etat courant ---
    public void activate() { state.activate(this); }
    public void suspend()  { state.suspend(this); }
    public void close()    { state.close(this); }
    public boolean login() { return state.login(this); }

    // --- utilises par les etats concrets ---
    void setState(AccountState newState) { this.state = newState; }
    /** Previent les observateurs (pattern OBSERVER). */
    public void announce(EventType type) { subject.notifyObservers(new AccountEvent(type, account)); }

    public StudentAccount getAccount() { return account; }
    public AccountStatus getStatus()   { return state.status(); }
    public AccountService getServices() { return services; }
    public void setServices(AccountService services) { this.services = services; }
}
