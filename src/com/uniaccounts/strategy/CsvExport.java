package com.uniaccounts.strategy;

import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.state.AccountContext;
import java.util.Collection;

/** Pattern: STRATEGY (role: ConcreteStrategy). Format : une ligne d'en-tete puis une ligne par compte. */
public class CsvExport implements ExportStrategy {
    @Override
    public String export(Collection<AccountContext> accounts) {
        StringBuilder sb = new StringBuilder("id,name,level,status\n");
        for (AccountContext c : accounts) {
            StudentAccount a = c.getAccount();
            sb.append(cell(a.getId())).append(',')
              .append(cell(a.getFullName())).append(',')
              .append(cell(a.getLevel())).append(',')
              .append(c.getStatus()).append('\n');
        }
        return sb.toString();
    }

    /** Met entre guillemets si la valeur contient une virgule. */
    private String cell(String v) { return v.contains(",") ? "\"" + v + "\"" : v; }
}
