package com.Grab.synapse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Grab.synapse.model.DisruptionResponse;
import com.Grab.synapse.tools.LogisticsTools;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;

@Service
public class AgentService {

    private final ChatModel model;
    private final List<ToolSpecification> toolSpecifications;
    private final ObjectMapper objectMapper;
    private final LogisticsTools tools;
    private final MessageWindowChatMemory memory;

    public AgentService() {
        this.model = OpenAiChatModel.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .apiKey("API-KEY")
                .modelName("llama-3.3-70b-versatile")
                .temperature(0.7)
                .timeout(Duration.ofMinutes(2))
                .build();

        this.toolSpecifications = LogisticsTools.getToolSpecifications();
        this.objectMapper = new ObjectMapper();
        this.tools = new LogisticsTools();

        this.memory = MessageWindowChatMemory.withMaxMessages(8);
    }

    public DisruptionResponse resolve(String scenario) {
        List<dev.langchain4j.data.message.ChatMessage> messages = new ArrayList<>();

        SystemMessage systemMessage = systemMessage("""
                You are an autonomous AI agent coordinating last-mile delivery disruptions.
                Use this reasoning loop:
                1. Understand the scenario.
                2. Identify what info/tools are needed.
                3. Select tools (from provided list).
                4. Use tools, observe results.
                5. Repeat until resolved.
                End with a clear resolution plan.
                """);
        messages.add(systemMessage);

        UserMessage initialUserMessage = userMessage(scenario);
        messages.add(initialUserMessage);

        StringBuilder chainOfThought = new StringBuilder();
        chainOfThought.append("User: ").append(scenario).append("\n");

        int maxIterations = 10;
        int iteration = 0;

        while (iteration < maxIterations) {
            ChatRequest request = ChatRequest.builder()
                    .messages(messages)
                    .toolSpecifications(toolSpecifications)
                    .build();

            ChatResponse response = model.chat(request);
            AiMessage aiMessage = response.aiMessage();
            messages.add(aiMessage);

            if (aiMessage.text() != null) {
                chainOfThought.append("AI: ").append(aiMessage.text()).append("\n");
            }

            if (aiMessage.hasToolExecutionRequests()) {
                chainOfThought.append("AI Tool Calls: ").append(aiMessage.toolExecutionRequests()).append("\n");
                for (ToolExecutionRequest ter : aiMessage.toolExecutionRequests()) {
                    String toolResult = tools.executeTool(ter.name(), ter.arguments());
                    ToolExecutionResultMessage term = ToolExecutionResultMessage.from(ter, toolResult);
                    messages.add(term);
                    chainOfThought.append("Tool Result (")
                            .append(ter.name())
                            .append("): ")
                            .append(toolResult)
                            .append("\n");
                }
            } else {
                return new DisruptionResponse(chainOfThought.toString(), aiMessage.text());
            }

            iteration++;
        }

        return new DisruptionResponse(chainOfThought.toString(),
                "Max iterations reached without final resolution.");
    }
}
