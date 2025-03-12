package org.example.pilates_helper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageLLMResponse(List<Data> data) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(String url) {}
}
