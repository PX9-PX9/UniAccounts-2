package com.uniaccounts.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern: COMPOSITE (role: Composite).
 * Un noeud de l'arbre : Universite > Faculte > Departement > Groupe > comptes.
 * Il contient d'autres composants et additionne leurs comptes (recursivite).
 */
public class OrgUnit extends OrgComponent {
    public enum Kind { UNIVERSITE, FACULTE, DEPARTEMENT, GROUPE }

    private final Kind kind;
    private final String name;
    private final List<OrgComponent> children = new ArrayList<>();

    public OrgUnit(Kind kind, String name) { this.kind = kind; this.name = name; }

    public void add(OrgComponent c)    { children.add(c); }
    public void remove(OrgComponent c) { children.remove(c); }

    /** Cherche un sous-noeud du meme nom, sinon le cree (pratique pour construire l'arbre). */
    public OrgUnit findOrCreate(Kind childKind, String childName) {
        for (OrgComponent c : children)
            if (c instanceof OrgUnit u && u.kind == childKind && u.name.equals(childName)) return u;
        OrgUnit created = new OrgUnit(childKind, childName);
        children.add(created);
        return created;
    }

    @Override public String getName() { return name; }

    @Override public int countAccounts() {
        int total = 0;
        for (OrgComponent c : children) total += c.countAccounts();   // appel recursif
        return total;
    }

    @Override public void print(String indent) {
        System.out.println(indent + kind + " " + name + " (" + countAccounts() + " comptes)");
        for (OrgComponent c : children) c.print(indent + "   ");
    }
}
