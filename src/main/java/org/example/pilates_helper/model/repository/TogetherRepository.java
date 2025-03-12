package org.example.pilates_helper.model.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pilates_helper.model.dto.*;
import org.example.pilates_helper.util.APIClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TogetherRepository implements APIClient {
    private TogetherRepository()
    {}
    private final static TogetherRepository instance = new TogetherRepository();
    public static TogetherRepository getInstance() {
        return instance;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String callAPI(TogetherAPIParam param) throws JsonProcessingException {
        String token = dotenv.get("TOGETHER_KEY");
        String method = "POST";
        String[] headers = new String[]{
                "Authorization", "Bearer %s".formatted(token),
                "Content-Type", "application/json"
        };
        String url, body, model;
        switch (param.modelType()) {
            case BASE:
                model = "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free";
                url = "https://api.together.xyz/v1/chat/completions";
                body = objectMapper.writeValueAsString(new BaseLLMBody(model, List.of(new BaseLLMBody.Message("user", param.prompt()))));
                break;
            case REASONING:
                model = "deepseek-ai/DeepSeek-R1-Distill-Llama-70B-free";
                url = "https://api.together.xyz/v1/chat/completions";
                body = objectMapper.writeValueAsString(new ReasoningLLMBody(model, List.of(new ReasoningLLMBody.Message("user", param.prompt())), 4096));
                break;
            case IMAGE:
                model = "black-forest-labs/FLUX.1-schnell-Free";
                url = "https://api.together.xyz/v1/images/generations";
                body = objectMapper.writeValueAsString(new ImageLLMBody(model, param.prompt(), 1024, 768, 4, 1, "url"));
                break;
        default:
                throw new RuntimeException("Unsupported together model type");
        }
        return APIClient.super.callAPI(new APIClientParam(url, method, body, headers));
    }
}
