package com.palindrome.check.service;

import com.palindrome.check.domain.PalindromeCheckInput;
import com.palindrome.check.repo.PalindromeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class PalindromeServiceTest {

    @Mock
    private PalindromeRepo palindromeFileWriterRepo;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private PalindromeService palindromeService;

    @Test
    public void testCheckIfPalindromeWorks() {
        Assertions.assertFalse(palindromeService.checkIfPalindrome(getCheckInput("ars")).isPalindrome());
        Assertions.assertTrue(palindromeService.checkIfPalindrome(getCheckInput("ara")).isPalindrome());
        Assertions.assertTrue(palindromeService.checkIfPalindrome(getCheckInput("")).isPalindrome());
    }

    private PalindromeCheckInput getCheckInput(String checkInput) {
        return PalindromeCheckInput.builder().input(checkInput).build();
    }
}
