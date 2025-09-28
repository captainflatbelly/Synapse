package com.Grab.synapse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisruptionResponse {

    private List<Map<String, String>> chainOfThought;
    private String finalPlan;

    public DisruptionResponse(List<Map<String, String>> chainOfThought, String finalPlan) {
        this.chainOfThought = chainOfThought;
        this.finalPlan = finalPlan;
    }

    public DisruptionResponse(String errorMessage, String finalPlan) {
        this.chainOfThought = new ArrayList<>();
        this.finalPlan = finalPlan;
    }

    public List<Map<String, String>> getChainOfThought() {
        return chainOfThought;
    }

    public void setChainOfThought(List<Map<String, String>> chainOfThought) {
        this.chainOfThought = chainOfThought;
    }

    public String getFinalPlan() {
        return finalPlan;
    }

    public void setFinalPlan(String finalPlan) {
        this.finalPlan = finalPlan;
    }
}
