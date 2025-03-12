package org.example.pilates_helper.model.dto;

import java.util.List;

public record BaseLLMBody(String model, List<Message> messages) {
    public record Message(String role, String content) { }
}
