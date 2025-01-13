package com.example.redis_autocomplete.entity;

import com.redis.om.spring.annotations.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Document(value = "product")
@Builder
public class Product {
    @Id
    private String id;

    @Searchable(sortable = true) // enables basic search operations
    @TextIndexed(weight = 2.0, nostem = false) //  enables full-text search capabilities for text fields
    @AutoComplete // enables autocomplete for the field
    private String name;

    @Searchable(sortable = true)
    @TextIndexed(weight = 1.5, nostem = false)
    @AutoComplete
    private String brandName;

    @Indexed
    private String skuCode;

    @TextIndexed(weight = 0.5, nostem = false)
    @Searchable
    //  stemming - particularly valuable because users might not use the exact form of the word that appears in your product data
    private String description;
}