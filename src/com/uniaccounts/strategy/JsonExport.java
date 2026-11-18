package com.uniaccounts.strategy;

import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.state.AccountContext;
import java.util.Collection;
import java.util.Iterator;

/** Pattern: STRATEGY (role: ConcreteStrategy). Format : tableau JSON d'objets. */
public class JsonExport implements ExportStrategy {
    @Override
    public String export(Collection<AccountContext> accounts) {
        StringBuilder sb = new StringBuilder("[\n");
        for (Iterator<AccountContext> it = accounts.iterator(); it.hasNext(); ) {
            AccountContext c = it.next();
            StudentAccount a = c.getAccount();
            sb.append("  {\"id\":\"").append(esc(a.getId()))
              .append("\",\"name\":\"").append(esc(a.getFullName()))
              .append("\",\"level\":\"").append(esc(a.getLevel()))
              .append("\",\"status\":\"").append(c.getStatus()).append("\"}");
            sb.append(it.hasNext() ? ",\n" : "\n");
        }
        return sb.append("]").toString();
    }

    private String esc(String v) { return v.replace("\\", "\\\\").replace("\"", "\\\""); }
}
