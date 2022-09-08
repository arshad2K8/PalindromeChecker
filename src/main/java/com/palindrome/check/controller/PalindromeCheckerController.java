package com.palindrome.check.controller;

import com.palindrome.check.domain.PalindromeCheckInput;
import com.palindrome.check.domain.PalindromeCheckResult;
import com.palindrome.check.service.PalindromeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/palindrome")
@Validated
@Slf4j
public class PalindromeCheckerController {

    private PalindromeService palindromeService;

    public PalindromeCheckerController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    @PostMapping("/check")
    PalindromeCheckResult checkIfPalindrome(@Valid @RequestBody PalindromeCheckInput palindromeCheckInput) {
        return palindromeService.checkIfPalindrome(palindromeCheckInput);
    }

}
