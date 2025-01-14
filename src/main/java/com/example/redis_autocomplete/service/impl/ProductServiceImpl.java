package com.example.redis_autocomplete.service.impl;

import com.example.redis_autocomplete.MatchType;
import com.example.redis_autocomplete.dto.AutoCompleteSuggestionResponse;
import com.example.redis_autocomplete.entity.Product;
import com.example.redis_autocomplete.repository.ProductRepository;
import com.example.redis_autocomplete.service.ProductService;
import com.github.javafaker.Faker;
import com.redis.om.spring.repository.query.QueryUtils;
import com.redis.om.spring.repository.query.Sort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Map<String,List<AutoCompleteSuggestionResponse>> getAutocompleteSuggestions(String searchTerm) {
        String[] terms = searchTerm.toLowerCase().split("\\s+");
        // Build prefix matches (word*)
        String prefixTerms = Arrays.stream(terms)
                .map(term -> term + "*")
                .collect(Collectors.joining(" | "));
        // Build fuzzy matches (%word%)
        String fuzzyTerms = Arrays.stream(terms)
                .map(term -> "%" + term + "%")
                .collect(Collectors.joining(" | "));
        List<Product> searchResult = productRepository.findByNameOrBrandNameOrDescription(
                prefixTerms,
                fuzzyTerms,
                searchTerm);
        if(!CollectionUtils.isEmpty(searchResult)) {
            return searchResult.stream()
                    .map(product -> {
                        if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                            return new AutoCompleteSuggestionResponse(product.getName(), MatchType.PRODUCT_MATCH.name(), product.getId(), product.getBrandName(), product.getDescription());
                        } else if (product.getBrandName().toLowerCase().contains(searchTerm.toLowerCase())) {
                            return new AutoCompleteSuggestionResponse(product.getName(), MatchType.BRAND_MATCH.name(), product.getId(), product.getBrandName(), product.getDescription());
                        } else if (product.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                            return new AutoCompleteSuggestionResponse(product.getName(), MatchType.DESCRIPTION_MATCH.name(), product.getId(),product.getBrandName(), product.getDescription());
                        } else
                            return new AutoCompleteSuggestionResponse(product.getName(), MatchType.SIMILAR_MATCH.name(), product.getId(),product.getBrandName(), product.getDescription());
                    })
                    .collect(Collectors.groupingBy(AutoCompleteSuggestionResponse::type,
                            Collectors.toList()));
        }
        return Map.of();
    }

    @Override
    public void generateSampleProducts(){
        Faker faker = new Faker();
        int totalSum = IntStream.range(0,100).peek(num->{
            Product product = Product
                    .builder()
                    .name(faker.commerce().productName())
                    .brandName(faker.company().name())
                    .description(faker.lorem().paragraph())
                    .skuCode(faker.idNumber().toString())
                    .build();
            productRepository.save(product);
        }).sum();
    }
}
