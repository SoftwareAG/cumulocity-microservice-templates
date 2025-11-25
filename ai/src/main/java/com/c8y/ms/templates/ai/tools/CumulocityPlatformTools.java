package com.c8y.ms.templates.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Component containing methods annotated with @Tool which provide example tools for the LLM.
 * These methods are exposed to the LLM for it to decide when to call them
 * based on the user's natural language request.
 * * NOTE: In a real Cumulocity microservice, these methods would inject and use
 * the C8Y Java SDK (e.g., AlarmApi, InventoryApi) to perform actual platform calls.
 */
@Service
public class CumulocityPlatformTools {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Tool to get the current system time. The LLM uses this to answer time-sensitive
     * questions or timestamp actions.
     *
     * @return The current date and time in a formatted string.
     */
    @Tool(description = "Get the current date and time on the Cumulocity server.")
    public String getCurrentServerDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).format(formatter);
    }


    /**
     * Tool to simulate the creation of a new Alarm in the Cumulocity platform (Example action).
     * The LLM must correctly parse the device ID, severity, and text from the user's query.
     *
     * @param deviceId The ID of the device to raise the alarm against.
     * @param severity The severity of the alarm (CRITICAL, MAJOR, MINOR, WARNING).
     * @param text     The descriptive text of the alarm (e.g., "Motor overheating").
     * @return A success message confirming the simulated action.
     */
    @Tool(description = "Creates a new alarm on a specified Cumulocity IoT device. Requires deviceId, severity, and a text message.")
    public String createPlatformAlarm(String deviceId, String severity, String text) {
        String logMessage = String.format(
                "ACTION_EXECUTED: Simulated alarm creation on device %s. Type: AI_GENERATED_ALARM. Severity: %s. Text: '%s'.",
                deviceId, severity.toUpperCase(), text);

        System.out.println(logMessage); // Log the action for observability

        return String.format("Alarm successfully created for device %s with severity %s.", deviceId, severity.toUpperCase());
    }
}