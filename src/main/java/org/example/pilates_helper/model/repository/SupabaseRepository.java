package org.example.pilates_helper.model.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pilates_helper.model.dto.*;
import org.example.pilates_helper.util.APIClient;

public class SupabaseRepository implements APIClient  {
    private SupabaseRepository()
    {}
    private final static SupabaseRepository instance = new SupabaseRepository();
    public static SupabaseRepository getInstance() {
        return instance;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

//    public LLMResult fineOne(String uuid) {
//        String method = "GET";
//        String[] headers = new String[]{
//                "apikey", token,
//                "Authorization", "Bearer %s".formatted(token),
//        };
//
//        return APIClient.super.callAPI(new APIClientParam("%s/%s?select=*&id=eq.Equal+".formatted(url, table), method, "", headers));
//    }

    String token = dotenv.get("SUPABASE_KEY");
    String url = dotenv.get("SUPABASE_URL");
    String table = dotenv.get("SUPABASE_TABLE");

    public String save(SupabaseAPIParam param) throws JsonProcessingException {
        String method = "POST";
        String[] headers = new String[]{
                "apikey", token,
                "Authorization", "Bearer %s".formatted(token),
                "Content-Type", "application/json",
                "Prefer", "return=minimal"
        };

        String body = objectMapper.writeValueAsString(param.data());
        return APIClient.super.callAPI(new APIClientParam("%s/%s".formatted(url, table), method, body, headers));
    }
}
