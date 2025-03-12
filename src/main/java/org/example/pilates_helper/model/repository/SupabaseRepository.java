package org.example.pilates_helper.model.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pilates_helper.model.dto.*;
import org.example.pilates_helper.util.APIClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SupabaseRepository implements APIClient  {
    private SupabaseRepository()
    {}
    private final static SupabaseRepository instance = new SupabaseRepository();
    public static SupabaseRepository getInstance() {
        return instance;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    final String token = dotenv.get("SUPABASE_KEY");
    final String url = dotenv.get("SUPABASE_URL");
    final String table = dotenv.get("SUPABASE_TABLE");

    public LLMResult fineOneByUUID(String uuid) {
        final String method = "GET";

        // 헤더 작성
        final String authorizationHeader = "Bearer " + token;
        final String[] headers = new String[]{
                "apikey", token,
                "Authorization", authorizationHeader
        };

        // 쿼리 파라미터 URL 인코딩
        String encodedUuid = URLEncoder.encode(uuid, StandardCharsets.UTF_8);
        String query = String.format("select=*&id=eq.%s", encodedUuid);
        String urlWithQuery = String.format("%s/%s?%s", url, table, query);

        // API 호출
        String response = APIClient.super.callAPI(new APIClientParam(urlWithQuery, method, "", headers));

        // JSON 응답을 List<LLMResult>로 변환 및 에러 처리
        try {
            List<LLMResult> results = objectMapper.readValue(response, new TypeReference<List<LLMResult>>() {});
            if (results != null && !results.isEmpty()) {
                return results.get(0);
            } else {
                throw new RuntimeException("응답 결과가 비어있습니다.");
            }
        } catch (IOException e) {
            throw new RuntimeException("응답 변환에 실패했습니다.", e);
        }
    }



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
