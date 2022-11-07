package com.example.demo.models;

import com.example.demo.enums.SecurityQuestions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityInfo {
    private String email;
    private String password;

    private SecurityQuestions securityQuestion;
    private String securityQuestionAnswer;
}
