package com.jekdev.saappapi.utils;


import java.util.Map;

public record SentientResult (String label, double score){}

public SentientResult analyzeTextHF(String text){
    String hfToken = "";
    return client.post()
            .uri("https://router.huggingface.co/hf-inference/distilbert/distilbert-base-uncased-finetuned-sst-2-english")
            .header("Authorization", "Bearer " + hfToken)
            .body(Map.of("inputs", text))
            .retrieve()
            .body(SentimentResult[].class)  // Antwort ist ein Array mit einem Objekt
            .block()[0];
}
