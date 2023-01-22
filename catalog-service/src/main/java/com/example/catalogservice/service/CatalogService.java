package com.example.catalogservice.service;

import com.example.catalogservice.entity.Catalogs;

public interface CatalogService {
    Iterable<Catalogs> getAllCatalogs();
}
