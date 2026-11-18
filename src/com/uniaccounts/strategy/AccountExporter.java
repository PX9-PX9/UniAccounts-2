package com.uniaccounts.strategy;

import com.uniaccounts.state.AccountContext;
import java.util.Collection;

/** Pattern: STRATEGY (role: Context). Utilise la strategie choisie ; on peut la changer a tout moment. */
public class AccountExporter {
    private ExportStrategy strategy;

    public AccountExporter(ExportStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(ExportStrategy strategy) { this.strategy = strategy; }

    public String export(Collection<AccountContext> accounts) { return strategy.export(accounts); }
}
