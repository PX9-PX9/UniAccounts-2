package com.uniaccounts;

import com.uniaccounts.adapter.LegacyStudentAdapter;
import com.uniaccounts.decorator.ServiceType;
import com.uniaccounts.facade.ImportReport;
import com.uniaccounts.facade.UniversityFacade;
import com.uniaccounts.legacy.LegacyStudentSystem;
import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.prototype.AccountTemplate;
import java.util.List;

/** Demonstration du Travail 2. Le client n'utilise que la FACADE (+ Builder/Prototype du Travail 1). */
public class MainWork2 {
    public static void main(String[] args) {
        UniversityFacade facade = new UniversityFacade();

        System.out.println("=== 1) Creation de comptes (Builder + Prototype du Travail 1) -> evenement CREATED ===");
        StudentAccount ali = new StudentAccount.Builder("20240001", "Ali Ben", "ali@univ.dz")
                .level("L2").major("Informatique").addCourse("POO").build();
        facade.register(ali);
        AccountTemplate promoL3 = new AccountTemplate("L3", "Informatique", false, List.of("POO", "Reseaux", "IA"));
        facade.register(promoL3.createAccount("20240101", "Nadia Ouali", "nadia@univ.dz"));
        facade.register(promoL3.createAccount("20240102", "Rida Benbordi", "rida@univ.dz"));

        System.out.println("\n=== 2) STATE : cycle de vie d'un compte ===");
        System.out.println("Connexion (PENDING)   : " + facade.login("20240001"));
        facade.activate("20240001");
        System.out.println("Connexion (ACTIVE)    : " + facade.login("20240001"));
        facade.suspend("20240001");
        System.out.println("Connexion (SUSPENDED) : " + facade.login("20240001"));
        facade.activate("20240001");                              // reactivation
        facade.close("20240001");
        System.out.println("Statut final           : " + facade.get("20240001").getStatus());
        try { facade.activate("20240001"); }                      // CLOSED -> plus rien n'est permis
        catch (IllegalStateException e) { System.out.println("Refuse : " + e.getMessage()); }
        facade.activate("20240101");                              // on garde d'autres comptes actifs
        facade.activate("20240102");
        facade.suspend("20240102");

        System.out.println("\n=== 3) ADAPTER : import depuis le systeme legacy -> evenement IMPORTED ===");
        ImportReport report = facade.importAccounts(new LegacyStudentAdapter(new LegacyStudentSystem()));
        System.out.println("Comptes importes : " + report.imported());
        System.out.println("Lignes refusees  : " + report.rejected().size());
        report.rejected().forEach(r -> System.out.println("   ! " + r));

        System.out.println("\n=== 4) COMPOSITE : arbre de l'universite ===");
        facade.assign("Faculte des Sciences", "Informatique", "Groupe 1", "20240101");
        facade.assign("Faculte des Sciences", "Informatique", "Groupe 1", "20240102");
        facade.assign("Faculte des Sciences", "Informatique", "Groupe 2", "19990001");
        facade.assign("Faculte des Sciences", "Informatique", "Groupe 2", "19990002");
        facade.assign("Faculte des Sciences", "Mathematiques", "Groupe 1", "19990003");
        facade.assign("Faculte de Droit", "Droit prive", "Groupe 1", "19990004");
        facade.getUniversity().print("");
        System.out.println("Total universite : " + facade.getUniversity().countAccounts() + " comptes");

        System.out.println("\n=== 5) DECORATOR : services supplementaires ===");
        facade.subscribe("20240101", ServiceType.LIBRARY);
        facade.subscribe("20240102", ServiceType.LIBRARY, ServiceType.RESTAURANT, ServiceType.INTERNET);
        for (String id : List.of("20240001", "20240101", "20240102"))
            System.out.println(id + " : " + facade.get(id).getServices().describe()
                    + "  (" + facade.get(id).getServices().monthlyFee() + " DA/mois)");

        System.out.println("\n=== 6) STRATEGY : export CSV puis JSON ===");
        System.out.println("--- CSV ---");
        System.out.println(facade.export("csv"));
        System.out.println("--- JSON ---");
        System.out.println(facade.export("json"));

        System.out.println("\n=== 7) OBSERVER : tableau de bord et journal d'audit ===");
        facade.printDashboard();
        System.out.println("--- Journal d'audit (" + facade.count() + " comptes) ---");
        facade.printAuditLog();
    }
}
