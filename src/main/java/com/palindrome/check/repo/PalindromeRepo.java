package com.palindrome.check.repo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface PalindromeRepo {
    void persistPalindromeCheckResult(String inputString, boolean result);
    List<Pair<String, Boolean>> getAllPalindromeChecks();
}
