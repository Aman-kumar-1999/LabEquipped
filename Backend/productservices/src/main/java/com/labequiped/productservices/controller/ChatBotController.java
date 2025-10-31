package com.labequiped.productservices.controller;

import com.labequiped.productservices.services.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping("/query")
    public ResponseEntity<Map<String, String>> handleQuery(
            @RequestBody Map<String, String> request,
            @RequestHeader(value = "userId", required = false) String userId) {
        
        String query = request.get("query");
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Query cannot be empty"));
        }

        String response = chatBotService.handleQuery(query, userId);
        return ResponseEntity.ok(Map.of("response", response));
    }
}