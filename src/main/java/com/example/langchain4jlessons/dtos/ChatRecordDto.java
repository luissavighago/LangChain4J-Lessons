package com.example.langchain4jlessons.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChatRecordDto(
    @NotBlank String question,
    String answer
) {
    @Override
    public String question() {
        return question;
    }

    @Override
    public String answer() {
        return answer;
    }
}
