package com.labequiped.productservices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GeminiClient {

    private final String apiKey = System.getenv("GEMINI_API_KEY");
    private final String endpoint = System.getenv("GEMINI_ENDPOINT");
    private final String model = System.getenv("GEMINI_MODEL");
    private final ObjectMapper mapper = new ObjectMapper();

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank() && endpoint != null && !endpoint.isBlank();
    }

    /**
     * Send a prompt to Gemini-ish endpoint and expect a JSON array of suggestion objects in response.
     * This method is intentionally generic: the exact Gemini/Vertex AI REST payload may vary.
     */
    public List<Map<String, Object>> requestSuggestions(String prompt, int limit) throws Exception {
        if (!isConfigured()) return new ArrayList<>();

        // Build a lightweight request body; users should adjust to match their provider's API
        String payload = mapper.writeValueAsString(Map.of(
                "model", model == null ? "gemini" : model,
                "prompt", prompt,
                "maxOutputTokens", 512,
                "temperature", 0.2,
                "topK", limit
        ));

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        // Use Authorization header if API expects an API key bearer token
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        int status = conn.getResponseCode();
        InputStream is = status >= 200 && status < 300 ? conn.getInputStream() : conn.getErrorStream();
        Map<?, ?> resp = mapper.readValue(is, Map.class);

        // Attempt to extract a suggestions array from common response shapes
        Object suggestionsObj = resp.get("suggestions");
        if (suggestionsObj == null) suggestionsObj = resp.get("items");
        if (suggestionsObj == null) suggestionsObj = resp.get("outputs");

        // If outputs is a structured AI output, try to parse JSON text from it
        if (suggestionsObj == null && resp.get("output") instanceof String) {
            String out = (String) resp.get("output");
            try {
                List<Map<String, Object>> parsed = mapper.readValue(out, List.class);
                return parsed;
            } catch (Exception e) {
                // ignore, will fallback
            }
        }

        if (suggestionsObj instanceof List) {
            return (List<Map<String, Object>>) suggestionsObj;
        }

        // fallback: return empty list
        return new ArrayList<>();
    }
}
