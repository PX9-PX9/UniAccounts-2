package com.uniaccounts.facade;

import com.uniaccounts.adapter.StudentSource;
import com.uniaccounts.audit.AuditLogger;
import com.uniaccounts.composite.AccountLeaf;
import com.uniaccounts.composite.OrgUnit;
import com.uniaccounts.decorator.*;
import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.observer.*;
import com.uniaccounts.repository.AccountRepository;
import com.uniaccounts.state.AccountContext;
import com.uniaccounts.strategy.*;
import java.util.*;

/**
 * Pattern: FACADE (role: Facade).
 * Interface simple qui cache la complexite : depot (Singleton), etats, observateurs, arbre,
 * export, services. Le client (Main) n'appelle QUE cette classe.
 */
public class UniversityFacade {
    private final AccountRepository repository = AccountRepository.getInstance();   // Singleton (Travail 1)
    private final AccountSubject subject = new AccountSubject();                    // Observer
    private final AdminDashboard dashboard = new AdminDashboard();
    private final Map<String, AccountContext> contexts = new LinkedHashMap<>();
    private final OrgUnit university = new OrgUnit(OrgUnit.Kind.UNIVERSITE, "Universite");  // Composite
    private final AccountExporter exporter = new AccountExporter(new CsvExport());          // Strategy

    public UniversityFacade() {
        subject.attach(new AuditObserver());
        subject.attach(new EmailNotifier());
        subject.attach(dashboard);
    }

    // ---------- creation / import ----------
    /** Enregistre un compte (construit avec le Builder ou clone depuis un Prototype). Il demarre en PENDING. */
    public AccountContext register(StudentAccount account) {
        repository.add(account);
        AccountContext ctx = new AccountContext(account, subject);
        contexts.put(account.getId(), ctx);
        ctx.announce(EventType.CREATED);
        return ctx;
    }

    /** Importe les comptes d'une source externe (via l'Adapter). Les doublons deja presents sont refuses. */
    public ImportReport importAccounts(StudentSource source) {
        int imported = 0;
        List<String> rejected = new ArrayList<>();
        for (StudentAccount a : source.fetchAccounts()) {
            try {
                repository.add(a);
                AccountContext ctx = new AccountContext(a, subject);
                contexts.put(a.getId(), ctx);
                ctx.announce(EventType.IMPORTED);
                imported++;
            } catch (IllegalStateException e) {
                rejected.add(a.getId() + "  ->  " + e.getMessage());
            }
        }
        rejected.addAll(source.getRejectedRecords());
        return new ImportReport(imported, rejected);
    }

    // ---------- cycle de vie (State) ----------
    public void activate(String id) { get(id).activate(); }
    public void suspend(String id)  { get(id).suspend(); }
    public void close(String id)    { get(id).close(); }
    public boolean login(String id) { return get(id).login(); }

    // ---------- services supplementaires (Decorator) ----------
    public void subscribe(String id, ServiceType... types) {
        AccountContext ctx = get(id);
        AccountService s = ctx.getServices();
        for (ServiceType t : types) {
            s = switch (t) {
                case LIBRARY    -> new LibraryService(s);
                case RESTAURANT -> new RestaurantService(s);
                case INTERNET   -> new InternetService(s);
            };
        }
        ctx.setServices(s);
    }

    // ---------- structure de l'universite (Composite) ----------
    public void assign(String faculty, String department, String group, String accountId) {
        OrgUnit g = university.findOrCreate(OrgUnit.Kind.FACULTE, faculty)
                              .findOrCreate(OrgUnit.Kind.DEPARTEMENT, department)
                              .findOrCreate(OrgUnit.Kind.GROUPE, group);
        g.add(new AccountLeaf(get(accountId)));
    }
    public OrgUnit getUniversity() { return university; }

    // ---------- export (Strategy) ----------
    public String export(String format) {
        exporter.setStrategy(format.equalsIgnoreCase("json") ? new JsonExport() : new CsvExport());
        return exporter.export(contexts.values());
    }

    // ---------- informations ----------
    public AccountContext get(String id) {
        AccountContext ctx = contexts.get(id);
        if (ctx == null) throw new NoSuchElementException("Compte introuvable : " + id);
        return ctx;
    }
    public int count() { return contexts.size(); }
    public void printDashboard() { dashboard.print(); }
    public void printAuditLog()  { AuditLogger.getInstance().printAll(); }
}
