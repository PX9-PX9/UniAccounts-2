package com.uniaccounts.observer;

import com.uniaccounts.model.StudentAccount;

/** Message envoye aux observateurs : quel evenement, sur quel compte. */
public class AccountEvent {
    private final EventType type;
    private final StudentAccount account;

    public AccountEvent(EventType type, StudentAccount account) { this.type = type; this.account = account; }
    public EventType getType() { return type; }
    public StudentAccount getAccount() { return account; }
}
