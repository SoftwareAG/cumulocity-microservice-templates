package com.c8y.ms.templates.ai.controller;

import com.c8y.ms.templates.ai.tools.CumulocityPlatformTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example REST controller demonstrating tools use with LLMs
 */
@RestController
@RequestMapping("/api/ai-agent/tools")
public class ToolController {

    private final ChatClient chatClient;

    public ToolController(ChatClient.Builder chatClientBuilder, CumulocityPlatformTools platformTools) {
        // This makes the tools accessible for all calls on this chatClient instance.
        this.chatClient = chatClientBuilder
                .defaultTools(platformTools)
                .build();
    }

    /**
     * Demonstrates an LLM calling a tool to get the current time.
     *
     * @param question The user's question.
     * @return The AI's response, which will be accurate due to the tool call.
     */
    @GetMapping("/get-time-context")
    public String getTimeContext(@RequestParam(value = "question", defaultValue = "What is today’s date on the server?") String question) {

        // The LLM decides whether to call getCurrentServerDateTime() before answering.
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    /**
     * Demonstrates an LLM calling a tool to execute a task (creating an alarm).
     *
     * @param actionQuery The user's request to perform an action.
     * @return The AI's response, confirming the action was executed.
     */
    @GetMapping("/take-action-alarm")
    public String takeActionAlarm(@RequestParam(value = "actionQuery",
            defaultValue = "Create a CRITICAL alarm for device C8Y-DEVICE-900 with the text 'Urgent: Motor failure detected.'")
                                  String actionQuery) {

        return chatClient.prompt()
                .user(actionQuery)
                .call()
                .content();
    }

    /**
     * Demonstrates the LLM selecting from multiple available tools (Time and Alarm).
     * The user's query requires the LLM to recognize which one is relevant.
     *
     * @param complexQuery A query that requires reasoning about available tools.
     * @return The AI's reasoned response.
     */
    @GetMapping("/multi-tool-test")
    public String multiToolTest(@RequestParam(value = "complexQuery",
            defaultValue = "I have a device C8Y-TEMP-101 that needs immediate attention. What is today's date, and can you set a MAJOR alarm for it saying 'System review needed'?")
                                String complexQuery) {

        // The LLM may perform two sequential or parallel tool calls based on its analysis:
        // 1. getCurrentServerDateTime()
        // 2. createPlatformAlarm(...)
        return chatClient.prompt()
                .user(complexQuery)
                .call()
                .content();
    }
}