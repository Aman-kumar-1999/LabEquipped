package com.labequiped.productservices.services;

import com.labequiped.productservices.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    public Page<Product> findAllPaged(int page, int size, String sortBy, String sortDir);


    public Optional<Product> getById(String id);


    public Page<Product> getAllCached(int page, int size, String sortBy, String sortDir);

    //public Product getAllCached(int page, int size, String sortBy, String sortDir);


    public Product createOrUpdate(Product product);

    public Product saveProduct(Product product, MultipartFile image) throws IOException;

    public byte[] getProductImage(String fileId) throws IOException;

    public Product getProductById(String id);

    public void delete(String id);

    // Filter helpers (all paged)
    public Page<Product> filterByProductName(String productName, Pageable pageable);

    public Page<Product> filterByCategory(String category, Pageable pageable);

    public Page<Product> filterByVariationId(String variationId, Pageable pageable);

    public Page<Product> filterByVendorId(String vendorId, Pageable pageable);

    public Page<Product> filterByBrandName(String brandName, Pageable pageable);

    public Page<Product> productNameOrBrand(String term, Pageable pageable);

    public long countAll();

    public long countByVendorId(String vendorId);

    public Map<String,Object> saveProductThroughExcel(MultipartFile file, String vendorEmail, String vendorId, String vendorName);


}
