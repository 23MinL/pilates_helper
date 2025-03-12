package org.example.pilates_helper.model.dto;

import java.util.Map;

public record APIClientParam(String url, String method, String body, String[] headers) {
}
