package com.uniaccounts.adapter;

import com.uniaccounts.model.StudentAccount;
import java.util.List;

/** Pattern: ADAPTER (role: Target). Ce que NOTRE systeme attend : des StudentAccount. */
public interface StudentSource {
    List<StudentAccount> fetchAccounts();
    /** Les lignes refusees (avec la raison), pour le rapport d'import. */
    List<String> getRejectedRecords();
}
