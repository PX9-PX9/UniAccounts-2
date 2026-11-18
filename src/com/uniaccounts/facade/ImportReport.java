package com.uniaccounts.facade;

import java.util.List;

/** Resultat d'un import : combien de comptes importes et quelles lignes ont ete refusees. */
public record ImportReport(int imported, List<String> rejected) { }
