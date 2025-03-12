package org.example.pilates_helper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pilates_helper.model.dto.BaseLLMResponse;
import org.example.pilates_helper.model.dto.ModelType;
import org.example.pilates_helper.model.dto.TogetherAPIParam;
import org.example.pilates_helper.model.repository.TogetherRepository;

public class TogetherService {
    private TogetherService() {}
    private static final TogetherService instance = new TogetherService();
    private ObjectMapper objectMapper = new ObjectMapper();
    private final TogetherRepository repository = TogetherRepository.getInstance();
    public static TogetherService getInstance() {
        return instance;
    }

    public String useBaseModel(String prompt) throws JsonProcessingException {
        String responseText = repository.callAPI(new TogetherAPIParam(
                prompt,
                ModelType.BASE
        ));
        return objectMapper.readValue(responseText, BaseLLMResponse.class).choices().get(0).message().content();
    }

    public String useReasoning(String prompt) throws JsonProcessingException {
        return repository.callAPI(new TogetherAPIParam(prompt, ModelType.REASONING));
    }

    public String useImage(String prompt) throws JsonProcessingException {
        return repository.callAPI(new TogetherAPIParam(prompt, ModelType.IMAGE));
    }
}
