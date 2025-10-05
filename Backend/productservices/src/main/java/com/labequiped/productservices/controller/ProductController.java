package com.labequiped.productservices.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.labequiped.productservices.entities.Product;
import com.labequiped.productservices.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.saveProduct(product, image));
    }




//    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public ResponseEntity<?> createProductDataWithUserData(
//            @RequestParam("file") MultipartFile file
////            @RequestPart("products") String product
//
//    ){
//        return ResponseEntity.ok("ok");
//
//    }



    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String id) throws IOException {
        Product product = productService.getProductById(id);
        if (product == null || product.getImageFileId() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageData = productService.getProductImage(product.getImageFileId());
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImageName() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // ⚡ or detect dynamically
                .body(imageData);
    }

    // Pagination / list (cached)
//    @GetMapping
//    public ResponseEntity<Page<Product>> list(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "productName") String sortBy,
//            @RequestParam(defaultValue = "ASC") String sortDir
//    ) {
//        return ResponseEntity.ok(productService.getAllCached(page, size, sortBy, sortDir));
//    }

    @GetMapping
    public ResponseEntity<List<Product>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        return ResponseEntity.ok(productService.getAllCached(page, size, sortBy, sortDir).getContent());
    }

    // filter endpoints
    @GetMapping("/filter/{productName}")
    public ResponseEntity<Page<Product>> filter(
            @PathVariable String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(productService.filterByProductName(productName, PageRequest.of(page, size)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> byCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.filterByCategory(category, PageRequest.of(page, size)));
    }

    @GetMapping("/variationId/{variationId}")
    public ResponseEntity<Page<Product>> byVariation(
            @PathVariable String variationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.filterByVariationId(variationId, PageRequest.of(page, size)));
    }

    @GetMapping("/vendorId/{vendorId}")
    public ResponseEntity<Page<Product>> byVendor(
            @PathVariable String vendorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.filterByVendorId(vendorId, PageRequest.of(page, size)));
    }

    @GetMapping("/productName/{productName}")
    public ResponseEntity<Page<Product>> byProductName(
            @PathVariable String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.filterByProductName(productName, PageRequest.of(page, size)));
    }

    @GetMapping("/productNameOrBrandName/{term}")
    public ResponseEntity<Page<Product>> productNameOrBrand(
            @PathVariable String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.productNameOrBrand(term, PageRequest.of(page, size)));
    }

    @GetMapping("/brandName/{brandName}")
    public ResponseEntity<Page<Product>> byBrand(
            @PathVariable String brandName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(productService.filterByBrandName(brandName, PageRequest.of(page, size)));
    }

    // Counts
    @GetMapping("/countAll")
    public ResponseEntity<Long> countAll() {
        return ResponseEntity.ok(productService.countAll());
    }

    @GetMapping("/countAllVendorId/{vendorId}")
    public ResponseEntity<Long> countByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(productService.countByVendorId(vendorId));
    }

    // Get by productId (two endpoints requested)
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getById(@PathVariable String productId) {
        Optional<Product> p = productService.getById(productId);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<Product> getById2(@PathVariable String productId) {
        return getById(productId);
    }

    // tier/productId/{productId} - returns tier data for this product (basic example)
    @GetMapping("/tier/productId/{productId}")
    public ResponseEntity<String> tierForProduct(@PathVariable String productId) {
        // placeholder: fetch product and return tierNo
        Optional<Product> p = productService.getById(productId);
        if (p.isPresent()) {
            return ResponseEntity.ok(p.get().getTierNo());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // upload (multipart)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "productId", required = false) String productId) {
        // Example: save to filesystem or cloud storage. Here we mock:
        String filename = file.getOriginalFilename();
        // TODO: store file to storage, set product.imageName / imagePath and save product
        return ResponseEntity.ok("Uploaded: " + filename);
    }

    // userUpdate - partial update by user
    @PatchMapping("/userUpdate/{productId}")
    public ResponseEntity<Product> userUpdate(@PathVariable String productId, @RequestBody Product partial) {
        Optional<Product> existing = productService.getById(productId);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        Product e = existing.get();
        // update allowed fields only (example: productDescription, productQuantity)
        if (partial.getProductDescription() != null) e.setProductDescription(partial.getProductDescription());
        if (partial.getProductQuantity() != null) e.setProductQuantity(partial.getProductQuantity());
        if (partial.getIndividualProductPrice() != 0) e.setIndividualProductPrice(partial.getIndividualProductPrice());
        Product saved = productService.createOrUpdate(e);
        return ResponseEntity.ok(saved);
    }

    // update full
    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable String productId, @RequestBody Product product) {
        product.setProductId(productId);
        Product saved = productService.createOrUpdate(product);
        return ResponseEntity.ok(saved);
    }

    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable String productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }


}
