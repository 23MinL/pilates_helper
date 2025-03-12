package org.example.pilates_helper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pilates_helper.model.dto.*;
import org.example.pilates_helper.model.repository.SupabaseRepository;

public class SupabaseService {
    private SupabaseService() {}
    private static final SupabaseService instance = new SupabaseService();
//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SupabaseRepository repository = SupabaseRepository.getInstance();
    public static SupabaseService getInstance() {
        return instance;
    }

    public void save(LLMResult data) throws JsonProcessingException {
        String response = repository.save(new SupabaseAPIParam(data));
//        System.out.println(response);
    }

    public LLMResult findByUUID(String uuid) {
        return repository.fineOneByUUID(uuid);
    }
}
