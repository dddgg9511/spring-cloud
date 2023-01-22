package com.example.catalogservice.repository;

import com.example.catalogservice.entity.Catalogs;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CatalogRepository extends CrudRepository<Catalogs, Long> {
    Optional<Catalogs> findByProductId(String productId);
}
