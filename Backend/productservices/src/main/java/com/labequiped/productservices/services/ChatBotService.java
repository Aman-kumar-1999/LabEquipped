package com.labequiped.productservices.services;

import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.ai.chat.ChatClient;
// import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatBotService {

    // @Autowired
    // private ChatClient chatClient;

    // private final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    private final OpenAIClient client;

    public ChatBotService(@Value("${spring.openai.api-key}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    @Autowired
    private ProductService productService;

    private static final String SYSTEM_PROMPT = """
        You are a helpful customer service chatbot for LabEquipped, a laboratory equipment e-commerce platform.
        You help customers with:
        1. Product recommendations
        2. Technical specifications
        3. Order status and shipping
        4. Returns and refunds
        5. General inquiries about lab equipment
        
        Keep responses concise and professional. If asked about specific products, 
        refer to actual products in our catalog. If unsure, guide users to contact support.
        """;

    public String handleQuery(String userQuery, String userId) {
        List<Message> messages = new ArrayList<>();
        messages.add(new UserMessage(SYSTEM_PROMPT));
        
        // Add context about available products
        StringBuilder context = new StringBuilder("Some of our popular products include: ");
        productService.getAllCached(0, 5, "productName", "ASC")
            .getContent()
            .forEach(p -> context.append(p.getProductName())
                                .append(" (")
                                .append(p.getCategory())
                                .append("), "));
        
        messages.add(new UserMessage(context.toString()));
        messages.add(new UserMessage(userQuery));

        // Create prompt with conversation history
        Prompt prompt = new Prompt(messages);

        
        try {
            //ChatResponse response = null;
            //OpenAIClient client = OpenAIOkHttpClient.fromEnv();
            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt.getContents())
                .model(ChatModel.GPT_5_NANO)
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);

        return chatCompletion.choices().get(0).message().content().orElse("No response");

            // (ChatResponse) chatClient.prompt(prompt);
            //return response.getResult().getOutput().getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "I apologize, but I'm having trouble processing your request right now. " +
                   "Please try again later or contact our support team for immediate assistance.";
        }
    }
}