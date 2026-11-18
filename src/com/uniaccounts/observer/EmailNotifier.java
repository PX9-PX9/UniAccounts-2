package com.uniaccounts.observer;

/** Pattern: OBSERVER (role: ConcreteObserver). Simule l'envoi d'un email a l'etudiant (affichage console). */
public class EmailNotifier implements AccountObserver {
    @Override
    public void update(AccountEvent e) {
        String message = switch (e.getType()) {
            case CREATED   -> "Votre compte a ete cree. Il attend son activation.";
            case ACTIVATED -> "Votre compte est actif.";
            case SUSPENDED -> "Votre compte a ete suspendu.";
            case CLOSED    -> "Votre compte a ete ferme.";
            case IMPORTED  -> "Votre ancien compte a ete importe dans le nouveau systeme.";
        };
        System.out.println("[EMAIL] a " + e.getAccount().getEmail() + " : " + message);
    }
}
