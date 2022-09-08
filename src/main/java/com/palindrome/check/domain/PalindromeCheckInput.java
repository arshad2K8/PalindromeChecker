package com.palindrome.check.domain;

import com.palindrome.check.validators.PalindromeInputConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PalindromeCheckInput {

    //@Pattern(regexp = "[\\S]*$")
    @PalindromeInputConstraint
    private String input;
}
