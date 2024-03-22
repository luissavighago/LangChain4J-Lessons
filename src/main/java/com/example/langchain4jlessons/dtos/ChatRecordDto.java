package com.example.langchain4jlessons.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChatRecordDto(
    @NotBlank String question
) {
    @Override
    public String question() {
        return question;
    }

}
