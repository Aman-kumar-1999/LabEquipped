package com.labequiped.productservices.repository;

import com.labequiped.productservices.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);
    Page<Product> findByVariationId(String variationId, Pageable pageable);
    Page<Product> findByVendorId(String vendorId, Pageable pageable);
    Page<Product> findByBrandNameContainingIgnoreCase(String brandName, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseOrBrandNameContainingIgnoreCase(
            String productName, String brandName, Pageable pageable);

    long countByVendorId(String vendorId);
}
