package com.c8y.ms.templates.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example REST controller demonstrating stateful conversational behavior using chat memory
 */
@RestController
@RequestMapping("/api/ai-agent/state-ful")
public class StatefulController {

    private final ChatClient chatClient;

    /**
     * Constructs the controller with a stateful {@link ChatClient}.
     * {@link MessageChatMemoryAdvisor}, allowing the AI to remember previous
     * interactions and maintain context across messages.
     *
     * @param chatMemory the memory store that preserves conversational state
     */
    public StatefulController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    /**
     * Example that sends a user message to the AI model with stateful memory enabled.
     * Example Requests:
     * /memory?message=Remember that my tenant is "tenant-test".
     * /memory?message=Which tenant am I working with?
     *
     * @param message the user's message to process
     * @return the AI-generated response as plain text
     */
    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}