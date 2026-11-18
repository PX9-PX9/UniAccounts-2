package com.uniaccounts.adapter;

import com.uniaccounts.legacy.LegacyStudentSystem;
import com.uniaccounts.model.StudentAccount;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Pattern: ADAPTER (role: Adapter - version "objet" par composition).
 * Adaptee = LegacyStudentSystem (bibliotheque externe, interdite de modification).
 * Il traduit les lignes texte "ID;LASTNAME;FIRSTNAME;EMAIL;LEVEL" en StudentAccount (via le Builder du Travail 1).
 *
 * Note : l'enonce annonce l'ordre LEVEL;EMAIL;FIRSTNAME;LASTNAME;ID, mais le commentaire ET les donnees du
 * fichier fourni suivent ID;LASTNAME;FIRSTNAME;EMAIL;LEVEL. On suit les donnees reelles.
 */
public class LegacyStudentAdapter implements StudentSource {
    private final LegacyStudentSystem legacy;
    private final List<String> rejected = new ArrayList<>();

    public LegacyStudentAdapter(LegacyStudentSystem legacy) { this.legacy = legacy; }

    @Override
    public List<StudentAccount> fetchAccounts() {
        rejected.clear();
        List<StudentAccount> result = new ArrayList<>();
        Set<String> seenIds = new HashSet<>();

        for (String line : legacy.fetchRecords()) {
            try {
                String[] p = line.split(";");
                if (p.length != 5) throw new IllegalArgumentException("format invalide (5 champs attendus)");

                String id    = p[0].trim();
                String last  = p[1].trim();
                String first = p[2].trim();
                String email = p[3].replace(" ", "").toLowerCase();     // nettoie "y. Rida@..."
                String level = p[4].trim().toUpperCase();

                if (!seenIds.add(id)) throw new IllegalArgumentException("id en double dans l'ancien systeme");

                String fullName = capitalize(first) + " " + last.toUpperCase();   // nom normalise
                result.add(new StudentAccount.Builder(id, fullName, email).level(level).build());
            } catch (IllegalArgumentException e) {
                rejected.add(line + "  ->  " + e.getMessage());
            }
        }
        return result;
    }

    @Override public List<String> getRejectedRecords() { return rejected; }

    private String capitalize(String s) {
        return s.isEmpty() ? s : s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
