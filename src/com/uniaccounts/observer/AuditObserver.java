package com.uniaccounts.observer;

import com.uniaccounts.audit.AuditLogger;

/** Pattern: OBSERVER (role: ConcreteObserver). Ecrit chaque evenement dans l'AuditLogger (Singleton du Travail 1). */
public class AuditObserver implements AccountObserver {
    @Override
    public void update(AccountEvent e) {
        AuditLogger.getInstance().log(e.getType() + " " + e.getAccount().getId());
    }
}
