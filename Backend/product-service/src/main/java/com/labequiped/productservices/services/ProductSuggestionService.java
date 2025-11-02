package com.labequiped.productservices.services;

import com.labequiped.productservices.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductSuggestionService {

    @Autowired
    private ProductService productService;
    @Autowired
    private GeminiClient geminiClient;


    public List<Product> getPersonalizedSuggestions(String userId, int limit) {
        // Get a mix of:
        // 1. Premium products (higher price range)
        // 2. Recently added products (using ID as proxy)
        // 3. Random selection from top categories
        
        List<Product> suggestions = new ArrayList<>();
        
        // Get all products (we'll optimize this later with proper queries)
        List<Product> allProducts = productService.getAllCached(0, 100, "productName", "ASC").getContent();
        
        if (allProducts.isEmpty()) {
            return Collections.emptyList();
        }

        // Add some premium products
        suggestions.addAll(
            allProducts.stream()
                .sorted((a, b) -> Float.compare(b.getIndividualProductPrice(), a.getIndividualProductPrice()))
                .limit(2)
                .collect(Collectors.toList())
        );

        // Add some recent products (using ID as proxy for recency)
        suggestions.addAll(
            allProducts.stream()
                .sorted((a, b) -> b.getProductId().compareTo(a.getProductId()))
                .limit(2)
                .collect(Collectors.toList())
        );

        // Add some random products for variety
        Random random = new Random();
        suggestions.addAll(
            allProducts.stream()
                .filter(p -> !suggestions.contains(p))
                .sorted((a, b) -> random.nextInt(3) - 1)
                .limit(2)
                .collect(Collectors.toList())
        );

        // Shuffle final list and limit size
        Collections.shuffle(suggestions);
        return suggestions.stream()
            .distinct()
            .limit(limit)
            .collect(Collectors.toList());
    }

    /**
     * Return AI-driven suggestions. If Gemini client is configured, call it; otherwise fall back
     * to local heuristic suggestions returned as a map list.
     */
    public List<Map<String, Object>> getAiSuggestions(Map<String, Object> user, int limit) {
        if (geminiClient != null && geminiClient.isConfigured()) {
            // build prompt with context
            List<Product> top = productService.getAllCached(0, 50, "productName", "ASC").getContent();
            StringBuilder prompt = new StringBuilder();
            prompt.append("Return a JSON array of product suggestion objects with fields: name, id, price, category, image. ");
            if (user != null && user.get("name") != null) {
                prompt.append("User: ").append(user.get("name")).append(". ");
            }
            prompt.append("Available product examples: ");
            for (int i = 0; i < Math.min(20, top.size()); i++) {
                Product p = top.get(i);
                prompt.append(p.getProductName()).append(";");
            }
            prompt.append("\nReturn at most ").append(limit).append(" suggestions as a pure JSON array.");

            try {
                List<Map<String, Object>> resp = geminiClient.requestSuggestions(prompt.toString(), limit);
                if (resp != null && !resp.isEmpty()) return resp;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback: use heuristic product suggestions and convert to maps
        List<Product> fallback = getPersonalizedSuggestions(user == null ? null : (String) user.get("id"), limit);
        List<Map<String, Object>> out = new ArrayList<>();
        for (Product p : fallback) {
            out.add(Map.of(
                    "id", p.getProductId(),
                    "sku", p.getProductId(),
                    "name", p.getProductName(),
                    "price", p.getIndividualProductPrice(),
                    "category", p.getCategory(),
                    "image", p.getImagePath()
            ));
        }
        return out;
    }
}