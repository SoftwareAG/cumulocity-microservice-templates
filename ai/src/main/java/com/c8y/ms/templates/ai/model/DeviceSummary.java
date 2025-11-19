package com.c8y.ms.templates.ai.model;

/**
 * Record to hold the structured summary of a Cumulocity Device.
 */
public record DeviceSummary(
        String deviceId,
        String overallStatus,
        String recommendedAction,
        String summaryText
) {}