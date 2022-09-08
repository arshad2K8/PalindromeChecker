package com.palindrome.check.domain;

public enum PalindromeResEnum {
    IS_PAL_TRUE(true), IS_PAL_FALSE(false);

    private PalindromeCheckResult palindromeCheckResult;

    PalindromeResEnum(boolean isPalindrome) {
        this.palindromeCheckResult = PalindromeCheckResult.builder()
                .palindrome(isPalindrome)
                .build();
    }

    public PalindromeCheckResult getPalindromeCheckResult() {
        return palindromeCheckResult;
    }
}
