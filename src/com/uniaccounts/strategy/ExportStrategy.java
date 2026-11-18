package com.uniaccounts.strategy;

import com.uniaccounts.state.AccountContext;
import java.util.Collection;

/** Pattern: STRATEGY (role: Strategy - interface). Une facon d'exporter une liste de comptes. */
public interface ExportStrategy {
    String export(Collection<AccountContext> accounts);
}
