package com.Grab.synapse.tools;

import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.agent.tool.ToolSpecification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LogisticsTools {

    public static List<ToolSpecification> getToolSpecifications() {
        return Arrays.asList(
                // Tool: getMerchantStatus
                ToolSpecification.builder()
                        .name("getMerchantStatus")
                        .description("Checks the status of a merchant, returning prep time or availability.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("merchantId", "The unique identifier of the merchant to check.")
                                .required("merchantId")
                                .build())
                        .build(),

                // Tool: notifyCustomer
                ToolSpecification.builder()
                        .name("notifyCustomer")
                        .description("Notifies the customer with a message and optional voucher.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("message", "The message to send to the customer.")
                                .addStringProperty("voucher", "An optional voucher code to offer the customer.")
                                .required("message")
                                .build())
                        .build(),

                // Tool: reRouteDriver
                ToolSpecification.builder()
                        .name("reRouteDriver")
                        .description("Re-routes a driver to a nearby task to optimize time.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("driverId", "The unique identifier of the driver.")
                                .addStringProperty("newTask", "The new task or destination for the driver.")
                                .required("driverId", "newTask")
                                .build())
                        .build(),

                // Tool: getNearbyMerchants
                ToolSpecification.builder()
                        .name("getNearbyMerchants")
                        .description("Gets nearby merchants with similar offerings and shorter wait times.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("location", "The location to search for merchants (e.g., city or coordinates).")
                                .addStringProperty("category", "The category of merchants to search for (e.g., food, grocery).")
                                .required("location", "category")
                                .build())
                        .build(),

                // Tool: initiateMediationFlow
                ToolSpecification.builder()
                        .name("initiateMediationFlow")
                        .description("Initiates a mediation flow between driver and customer for dispute resolution.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("orderId", "The unique identifier of the order.")
                                .required("orderId")
                                .build())
                        .build(),

                // Tool: collectEvidence
                ToolSpecification.builder()
                        .name("collectEvidence")
                        .description("Collects evidence from driver and customer via photos and questions.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("orderId", "The unique identifier of the order.")
                                .required("orderId")
                                .build())
                        .build(),

                // Tool: analyzeEvidence
                ToolSpecification.builder()
                        .name("analyzeEvidence")
                        .description("Analyzes collected evidence to determine fault in a dispute.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("evidenceData", "The evidence data to analyze (e.g., photo metadata or questionnaire answers).")
                                .required("evidenceData")
                                .build())
                        .build(),

                // Tool: issueInstantRefund
                ToolSpecification.builder()
                        .name("issueInstantRefund")
                        .description("Issues an instant refund to the customer for an order.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("orderId", "The unique identifier of the order.")
                                .addNumberProperty("amount", "The refund amount in the local currency.")
                                .required("orderId", "amount")
                                .build())
                        .build(),

                // Tool: exonerateDriver
                ToolSpecification.builder()
                        .name("exonerateDriver")
                        .description("Exonerates the driver from fault in a dispute.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("driverId", "The unique identifier of the driver.")
                                .required("driverId")
                                .build())
                        .build(),

                // Tool: logMerchantPackagingFeedback
                ToolSpecification.builder()
                        .name("logMerchantPackagingFeedback")
                        .description("Logs feedback to the merchant about packaging issues.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("merchantId", "The unique identifier of the merchant.")
                                .addStringProperty("feedback", "The feedback about packaging issues.")
                                .required("merchantId", "feedback")
                                .build())
                        .build(),

                // Tool: notifyResolution
                ToolSpecification.builder()
                        .name("notifyResolution")
                        .description("Notifies both parties of the resolution outcome.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("message", "The resolution message to send to both parties.")
                                .required("message")
                                .build())
                        .build(),

                // Tool: contactRecipientViaChat
                ToolSpecification.builder()
                        .name("contactRecipientViaChat")
                        .description("Contacts the recipient via chat with automated prompts.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("recipientId", "The unique identifier of the recipient.")
                                .addStringProperty("prompt", "The automated prompt to send to the recipient.")
                                .required("recipientId", "prompt")
                                .build())
                        .build(),

                // Tool: suggestSafeDropOff
                ToolSpecification.builder()
                        .name("suggestSafeDropOff")
                        .description("Suggests a safe drop-off location for a package.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("location", "The current location of the delivery.")
                                .required("location")
                                .build())
                        .build(),

                // Tool: findNearbyLocker
                ToolSpecification.builder()
                        .name("findNearbyLocker")
                        .description("Finds nearby secure parcel lockers for delivery.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("location", "The location to search for lockers (e.g., city or coordinates).")
                                .required("location")
                                .build())
                        .build(),

                // Tool: checkTraffic
                ToolSpecification.builder()
                        .name("checkTraffic")
                        .description("Checks traffic conditions on a specified route.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("route", "The route to check for traffic conditions.")
                                .required("route")
                                .build())
                        .build(),

                // Tool: calculateAlternativeRoute
                ToolSpecification.builder()
                        .name("calculateAlternativeRoute")
                        .description("Calculates an alternative route to avoid traffic or obstructions.")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("originalRoute", "The original route to find an alternative for.")
                                .required("originalRoute")
                                .build())
                        .build(),

                // Tool: notifyPassengerAndDriver
                ToolSpecification.builder()
                        .name("notifyPassengerAndDriver")
                        .description("Notifies both passenger and driver of updates (e.g., route changes).")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("message", "The update message to send to both parties.")
                                .required("message")
                                .build())
                        .build(),

                // Tool: checkFlightStatus
                ToolSpecification.builder()
                        .name("checkFlightStatus")
                        .description("Checks the status of a flight (hypothetical).")
                        .parameters(JsonObjectSchema.builder()
                                .addStringProperty("flightNumber", "The flight number to check.")
                                .required("flightNumber")
                                .build())
                        .build()
        );
    }

    public String executeTool(String name, String arguments) {
        try {
            com.fasterxml.jackson.databind.JsonNode argsNode = new com.fasterxml.jackson.databind.ObjectMapper().readTree(arguments);

            switch (name) {
                case "getMerchantStatus":
                    String merchantId = argsNode.get("merchantId").asText();
                    return getMerchantStatus(merchantId);

                case "notifyCustomer":
                    String message = argsNode.get("message").asText();
                    String voucher = argsNode.has("voucher") ? argsNode.get("voucher").asText() : null;
                    return notifyCustomer(message, voucher);

                case "reRouteDriver":
                    String driverId = argsNode.get("driverId").asText();
                    String newTask = argsNode.get("newTask").asText();
                    return reRouteDriver(driverId, newTask);

                case "getNearbyMerchants":
                    String location = argsNode.get("location").asText();
                    String category = argsNode.get("category").asText();
                    return getNearbyMerchants(location, category);

                case "initiateMediationFlow":
                    String orderId = argsNode.get("orderId").asText();
                    return initiateMediationFlow(orderId);

                case "collectEvidence":
                    String orderIdCollect = argsNode.get("orderId").asText();
                    return collectEvidence(orderIdCollect);

                case "analyzeEvidence":
                    String evidenceData = argsNode.get("evidenceData").asText();
                    return analyzeEvidence(evidenceData);

                case "issueInstantRefund":
                    String orderIdRefund = argsNode.get("orderId").asText();
                    double amount = argsNode.get("amount").asDouble();
                    return issueInstantRefund(orderIdRefund, amount);

                case "exonerateDriver":
                    String driverIdEx = argsNode.get("driverId").asText();
                    return exonerateDriver(driverIdEx);

                case "logMerchantPackagingFeedback":
                    String merchantIdFb = argsNode.get("merchantId").asText();
                    String feedback = argsNode.get("feedback").asText();
                    return logMerchantPackagingFeedback(merchantIdFb, feedback);

                case "notifyResolution":
                    String messageRes = argsNode.get("message").asText();
                    return notifyResolution(messageRes);

                case "contactRecipientViaChat":
                    String recipientId = argsNode.get("recipientId").asText();
                    String prompt = argsNode.get("prompt").asText();
                    return contactRecipientViaChat(recipientId, prompt);

                case "suggestSafeDropOff":
                    String locationSd = argsNode.get("location").asText();
                    return suggestSafeDropOff(locationSd);

                case "findNearbyLocker":
                    String locationLocker = argsNode.get("location").asText();
                    return findNearbyLocker(locationLocker);

                case "checkTraffic":
                    String route = argsNode.get("route").asText();
                    return checkTraffic(route);

                case "calculateAlternativeRoute":
                    String originalRoute = argsNode.get("originalRoute").asText();
                    return calculateAlternativeRoute(originalRoute);

                case "notifyPassengerAndDriver":
                    String messagePd = argsNode.get("message").asText();
                    return notifyPassengerAndDriver(messagePd);

                case "checkFlightStatus":
                    String flightNumber = argsNode.get("flightNumber").asText();
                    return checkFlightStatus(flightNumber);

                default:
                    return "Unknown tool: " + name;
            }
        } catch (Exception e) {
            return "Error executing tool " + name + ": " + e.getMessage();
        }
    }

    private String getMerchantStatus(String merchantId) {
        return "Merchant " + merchantId + " has 40-minute prep time.";
    }

    private String notifyCustomer(String message, String voucher) {
        return "Notified customer: " + message + (voucher != null ? " with voucher " + voucher : "");
    }

    private String reRouteDriver(String driverId, String newTask) {
        return "Re-routed driver " + driverId + " to " + newTask;
    }

    private String getNearbyMerchants(String location, String category) {
        return "Found nearby merchants: MerchantA (10 min), MerchantB (15 min)";
    }

    private String initiateMediationFlow(String orderId) {
        return "Mediation flow initiated for order " + orderId;
    }

    private String collectEvidence(String orderId) {
        return "Evidence collected: Photos and answers received.";
    }

    private String analyzeEvidence(String evidenceData) {
        return "Analysis: Merchant fault detected.";
    }

    private String issueInstantRefund(String orderId, double amount) {
        return "Refund issued for " + orderId + ": $" + amount;
    }

    private String exonerateDriver(String driverId) {
        return "Driver " + driverId + " exonerated.";
    }

    private String logMerchantPackagingFeedback(String merchantId, String feedback) {
        return "Feedback logged to merchant " + merchantId + ": " + feedback;
    }

    private String notifyResolution(String message) {
        return "Resolution notified: " + message;
    }

    private String contactRecipientViaChat(String recipientId, String prompt) {
        return "Contacted recipient " + recipientId + ": " + prompt;
    }

    private String suggestSafeDropOff(String location) {
        return "Suggested safe drop-off: " + location;
    }

    private String findNearbyLocker(String location) {
        return "Found locker at: LockerXYZ near " + location;
    }

    private String checkTraffic(String route) {
        return "Major accident on " + route;
    }

    private String calculateAlternativeRoute(String originalRoute) {
        return "Alternative route found: New path with ETA +10 min";
    }

    private String notifyPassengerAndDriver(String message) {
        return "Notified passenger and driver: " + message;
    }

    private String checkFlightStatus(String flightNumber) {
        return "Flight " + flightNumber + " is delayed by 30 min.";
    }
}