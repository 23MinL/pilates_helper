package org.example.pilates_helper.model.dto;

import java.util.List;

public record ReasoningLLMBody(String model, List<Message> messages, int max_tokens) {
    public record Message(String role, String content) { }
}
