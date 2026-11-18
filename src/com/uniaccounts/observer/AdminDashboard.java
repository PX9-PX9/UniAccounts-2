package com.uniaccounts.observer;

import java.util.EnumMap;
import java.util.Map;

/** Pattern: OBSERVER (role: ConcreteObserver). Compte les evenements par type et les affiche. */
public class AdminDashboard implements AccountObserver {
    private final Map<EventType, Integer> counts = new EnumMap<>(EventType.class);

    @Override
    public void update(AccountEvent e) { counts.merge(e.getType(), 1, Integer::sum); }

    public int count(EventType type) { return counts.getOrDefault(type, 0); }

    public void print() {
        System.out.println("=== Tableau de bord admin ===");
        for (EventType t : EventType.values())
            System.out.println(String.format("%-10s : %d", t, count(t)));
    }
}
