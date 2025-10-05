package com.labequiped.productservice.service;

import com.labequiped.productservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
public interface ProductService {

    public Page<Product> findAllPaged(int page, int size, String sortBy, String sortDir);


    public Optional<Product> getById(String id);


    public Page<Product> getAllCached(int page, int size, String sortBy, String sortDir);


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

//    public Map<String, Object> createProductData(
//            MultipartFile images,
//            String vendorId,
//            String brandName,
//            String vendorName,
//            String vendorEmail,
//            String productName,
//            String productDescription,
//            String category,
//            Integer productQuantity,
//            float individualProductPrice,
//            float discountPercentage,
//            String status,
//            String action,
//            String bulkCode,
//            String variationName,
//            String variationId,
//            float gst,
//            String hsn,
//            String isVerified,
//            String tierNo,
//            String containLiquid,
//            String companyCode,
//            String bulkPack,
//            float bulkPrice
//    );
//
//    public Map<String, Object> updateProductData(
//            String productId,
//            MultipartFile images,
//            String vendorId,
//            String brandName,
//            String vendorName,
//            String vendorEmail,
//            String productName,
//            String productDescription,
//            String category,
//            Integer productQuantity,
//            float individualProductPrice,
//            float discountPercentage,
//            String status,
//            String action,
//            String bulkCode,
//            String variationName,
//            String variationId,
//            float gst,
//            String hsn,
//            String isVerified,
//            String tierNo,
//            String containLiquid,
//            String companyCode,
//            String bulkPack,
//            float bulkPrice
//    );
//    public Product getProductDataByProductId(String productId);
//
//    public Integer countOfAllProduct();
//
//    Integer countOfAllProductWithVendorId(String vendorId);
//
//    public List<Product> getAllProductByBrandName(String brandName, int pageNo1, int dataLimit1);
//
//    public List<Product> getAllProductByProductName(String productName, int pageNo1, int dataLimit1);
//
//    public List<Product> getAllProductByProductNameOrBrandName(String productName, int pageNo1, int dataLimit1);
//
//    public List<Product> getProductByProductName(String productName);
//
//    public List<Product> getAllProductData();
//
//    public List<Product> getAllProductDataWithRange(int pageNo1, int dataLimit1);
//
//    public List<Product> getAllProductCategory(String category);
//    public List<Product> getAllProductCategoryWithRange(String category, int pageNo1, int dataLimit1);
//
//    public List<Product> getAllProductVendorId(String vendorId);
//
//    public List<Product> getAllProductVendorIdByRange(String vendorId, int pageNo, int dataLimit);
//
//    public List<Product> getAllProductVariationId(String variationId);
//
//    public List<Product> getAllProductVariationIdByRange(String variationId, int pageNo1, int dataLimit1);
//
//    public String deleteProductData(String productId);
//
//    public Map<String,Object> saveProductThroughExcel(MultipartFile file, String vendorEmail, String vendorId, String vendorName);

}
