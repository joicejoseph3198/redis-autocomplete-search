package com.example.redis_autocomplete.service;

import com.example.redis_autocomplete.dto.AutoCompleteSuggestionResponse;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String,List<AutoCompleteSuggestionResponse>> getAutocompleteSuggestions(String searchTerm);
    void generateSampleProducts();
}
