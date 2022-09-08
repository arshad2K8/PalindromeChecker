package com.palindrome.check.service;
import com.palindrome.check.domain.PalindromeCheckInput;
import com.palindrome.check.domain.PalindromeCheckResult;
import com.palindrome.check.repo.PalindromeDBRepo;
import com.palindrome.check.repo.PalindromeFileWriterRepo;
import com.palindrome.check.repo.PalindromeRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.palindrome.check.domain.PalindromeResEnum.IS_PAL_FALSE;
import static com.palindrome.check.domain.PalindromeResEnum.IS_PAL_TRUE;

@Service
@Slf4j
public class PalindromeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PalindromeService.class);

    private PalindromeRepo palindromeFileWriterRepo;
    private CacheManager cacheManager;


    public PalindromeService(PalindromeRepo palindromeFileWriterRepo, CacheManager cacheManager) {
        this.palindromeFileWriterRepo = palindromeFileWriterRepo;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "palindromes", key = "#palindromeCheckInput.input")
    public PalindromeCheckResult checkIfPalindrome(PalindromeCheckInput palindromeCheckInput) {
        PalindromeCheckResult checkResult = getCheckResult(isPalindrome(palindromeCheckInput.getInput()));
        persistAsyncToAFile(palindromeCheckInput, checkResult);
        return checkResult;
    }

    private void persistAsyncToAFile(PalindromeCheckInput palindromeCheckInput, PalindromeCheckResult checkResult) {
        CompletableFuture
                .runAsync(() -> this.palindromeFileWriterRepo
                        .persistPalindromeCheckResult(palindromeCheckInput.getInput(), checkResult.isPalindrome()))
                .exceptionally(x -> {
                    LOGGER.error("Error while persisting ", x);
                    return null;
                });
    }

    private boolean isPalindrome(String text) {
        if (StringUtils.isBlank(text)) {
            return true;
        }
        String reversedString = new StringBuilder(text.toLowerCase()).reverse().toString();
        return StringUtils.equals(text.toLowerCase(), reversedString);
    }

    @EventListener(classes = ApplicationStartedEvent.class )
    public void initCache(ApplicationStartedEvent event) {
        Cache palindromesCache = cacheManager.getCache("palindromes");
        if (palindromesCache != null) {
            this.palindromeFileWriterRepo.getAllPalindromeChecks()
                    .forEach(palCheck -> {
                        LOGGER.info("writing palCheck to cache {}", palCheck.getLeft());
                        palindromesCache.put(palCheck.getLeft(), getCheckResult(palCheck.getRight()));
                    });
        } else {
            LOGGER.error("palindromesCache is null");
        }
        LOGGER.info("Finished init cache ");
    }

    private PalindromeCheckResult getCheckResult(boolean result) {
        return result ? IS_PAL_TRUE.getPalindromeCheckResult() : IS_PAL_FALSE.getPalindromeCheckResult();
    }
}
