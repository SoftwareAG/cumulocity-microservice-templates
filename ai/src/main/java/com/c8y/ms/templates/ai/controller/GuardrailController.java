package com.c8y.ms.templates.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-agent/guardrails")
public class GuardrailController {

    private final ChatClient chatClient;

    /**
     * Defines the System Message constant for the typical guardrail.
     */
    private static final String C8Y_GUARDRAIL_SYSTEM_MESSAGE =
            """
            You are a helpful, specialized Cumulocity IoT assistant.
            
            Your sole purpose is to answer questions related to Cumulocity IoT, its API, concepts,
            microservices, devices, and data modeling (Managed Objects, Measurements, Alarms, Events).
            
            If the user asks a question on any other topic (e.g., history, politics, general knowledge,
            finance, or cooking), you MUST politely refuse and respond ONLY with the following canned phrase:
            
            'I can only help with Cumulocity IoT related questions. Please ask me about devices, data, or microservices.'
            
            Do not provide any other answer if the topic is out of scope.
            """;

    public GuardrailController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    /**
     * Simple example of processes a user query through the AI chat client with Cumulocity-specific guardrails applied.
     *
     * @param userQuery the user's input prompt; defaults to a simple explanation request
     *                  for a Cumulocity IoT ManagedObject if not provided
     * @return the AI-generated response content as a {@code String}
     */
    @GetMapping("/chat")
    public String chat(@RequestParam(value = "query", defaultValue = "Explain the concept of a ManagedObject in Cumulocity IoT in simple terms.") String userQuery) {

        return chatClient.prompt()
                .system(C8Y_GUARDRAIL_SYSTEM_MESSAGE)
                .user(userQuery)
                .call()
                .content();
    }
}