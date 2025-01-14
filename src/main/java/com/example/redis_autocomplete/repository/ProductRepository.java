package com.example.redis_autocomplete.repository;

import com.example.redis_autocomplete.entity.Product;
import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

    public interface ProductRepository extends RedisDocumentRepository<Product,String> {
    // Combined fuzzy, prefix matching, and full-text search
    @Query(value = "(@name:(($prefixTerms) |($fuzzyTerms) | \"$fullPhrase\")) |(@brandName:(($prefixTerms) |\"$fullPhrase\")) |(@description:\"$fullPhrase\")")
    List<Product> findByNameOrBrandNameOrDescription(
            @Param("prefixTerms") String prefixTerms,
            @Param("fuzzyTerms") String fuzzyTerms,
            @Param("fullPhrase") String fullPhrase
    );
}
