package com.labequiped.productservice.controller;



//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.labequiped.productservice.config.FileUpload;
import com.labequiped.productservice.entities.Product;
import com.labequiped.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
//@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private FileUpload fileUpload;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProductDataWithUserData(
            @RequestParam("file") MultipartFile file
           //@RequestPart("products") String product

    ){
        return ResponseEntity.ok("ok");

    }

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
    @GetMapping
    public ResponseEntity<Page<Product>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        return ResponseEntity.ok(productService.getAllCached(page, size, sortBy, sortDir));
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














//
//import com.labequiped.productservice.config.FileUpload;
//import com.labequiped.productservice.entities.*;
//import com.labequiped.productservice.helper.Helper;
//import com.labequiped.productservice.repository.BrandRepository;
//import com.labequiped.productservice.repository.VariationRepository;
//import com.labequiped.productservice.service.ProductService;
//import com.labequiped.productservice.service.TierService;
//import com.labequiped.productservice.service.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/product")
//@CrossOrigin("*")
//public class ProductController {
//
//
//    @Autowired
//    private FileUpload fileUpload;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private VariationRepository variationRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    private TierService tierService;
//
//    @Autowired
//    private BrandRepository brandRepository;
//
//
//    @PostMapping("/")
//    public ResponseEntity<?> createProductDataWithUserData(
//            @RequestParam("images") MultipartFile images,
//            @RequestParam("products") String product
//
//    ){
//        Product product1 = null;
//        try {
//            product1 = objectMapper.readValue(product,Product.class);
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//            //throw new RuntimeException(e);
//
//        }
//        Map<String,Object> map = new HashMap<>();
//        map = productService.createProductData(
//                images,
//                product1.getVendorId(),
//                product1.getVendorName(),
//                product1.getBrandName(),
//                product1.getVendorEmail(),
//                product1.getProductName(),
//                product1.getProductDescription(),
//                product1.getCategory(),
//                product1.getProductQuantity(),
//                product1.getIndividualProductPrice(),
//                product1.getDiscountPercentage(),
//                product1.getStatus(),
//                product1.getAction(),
//                product1.getBulkCode(),
//                product1.getVariationName(),
//                product1.getVariationId(),
//                product1.getGst(),
//                product1.getHsn(),
//                product1.getIsVerified(),
//                product1.getTierNo(),
//                product1.getContainLiquid(),
//                product1.getCompanyCode(),
//                product1.getBulkPack(),
//                product1.getBulkPrice()
//        );
//
//        return ResponseEntity.ok(map);
//    }
//    @PutMapping("/")
//    public ResponseEntity<?> updateProductData(
//            @RequestParam("images") MultipartFile images,
//            @RequestParam("products") String product
//    ){
//        Product product1 = null;
//        try {
//            product1 = objectMapper.readValue(product,Product.class);
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//            //throw new RuntimeException(e);
//
//        }
//
//        Map<String,Object> map = new HashMap<>();
//        map = productService.updateProductData(
//                product1.getProductId(),
//                images,
//                product1.getVendorId(),
//                product1.getVendorName(),
//                product1.getBrandName(),
//                product1.getVendorEmail(),
//                product1.getProductName(),
//                product1.getProductDescription(),
//                product1.getCategory(),
//                product1.getProductQuantity(),
//                product1.getIndividualProductPrice(),
//                product1.getDiscountPercentage(),
//                product1.getStatus(),
//                product1.getAction(),
//                product1.getBulkCode(),
//                product1.getVariationName(),
//                product1.getVariationId(),
//                product1.getGst(),
//                product1.getHsn(),
//                product1.getIsVerified(),
//                product1.getTierNo(),
//                product1.getContainLiquid(),
//                product1.getCompanyCode(),
//                product1.getBulkPack(),
//                product1.getBulkPrice()
//        );
//
//        return ResponseEntity.ok(map);
//    }
//
//
////    @GetMapping("/")
////    public ResponseEntity<?> getAllProductData(){
////        return ResponseEntity.ok(productService.getAllProductData());
////    }
//
//    @GetMapping("/")
//    public ResponseEntity<?> getAllProductDataWithRange(@RequestParam(value = "pageNo", defaultValue = "1" , required = false) int pageNo,@RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
//        return ResponseEntity.ok(productService.getAllProductDataWithRange(pageNo, dataLimit));
//    }
//
//    @GetMapping("/filter/{productName}")
//    public ResponseEntity<?> getAllProductByProductName(@PathVariable String productName){
//
//        return ResponseEntity.ok(productService.getProductByProductName(productName));
//
//    }
//
//    @GetMapping("/category/{category}")
//    public ResponseEntity<?> getAllProductCategoryWithRange(@PathVariable("category") String category, @RequestParam(value="pageNo",defaultValue = "1", required = false) int pageNo,@RequestParam(value = "dataLimit",defaultValue = "10" ,required = false ) int dataLimit){
//        List<Product> products = productService.getAllProductCategoryWithRange(category,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
////    @GetMapping("/category/{category}")
////    public ResponseEntity<?> getAllProductCategory(@PathVariable("category") String category){
////        List<Product> products = productService.getAllProductCategory(category);
////
////        return ResponseEntity.ok(products);
////    }
//
//
//
////    @GetMapping("/variationId/{variationId}")
////    public ResponseEntity<?> getAllProductCategoryText(@PathVariable String variationId){
////        List<Product> products = productService.getAllProductVariationId(variationId);
////
////        return ResponseEntity.ok(products);
////    }
//
//
//
//
//
//    @GetMapping("/variationId/{variationId}")
//    public ResponseEntity<?> getAppProductWithVariationByRange(@PathVariable String variationId,
//                                                               @RequestParam(value = "pageNo" , defaultValue = "1" , required = false) Integer pageNo,
//                                                               @RequestParam(value = "dataLimit", defaultValue = "10", required = false) Integer dataLimit){
//        List<Product> products = productService.getAllProductVariationIdByRange(variationId,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
//
//
////    @GetMapping("/vendorId/{vendorId}")
////    public ResponseEntity<?> getAllProductVendorId(@PathVariable String vendorId){
////
////        List<Product> products = productService.getAllProductVendorId(vendorId);
////
////        return ResponseEntity.ok(products);
////    }
//
//    @GetMapping("/vendorId/{vendorId}")
//    public ResponseEntity<?> getAllProductVendorIdByRange(@PathVariable("vendorId") String vendorId,
//                                                          @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
//                                                          @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
//
//        List<Product> products = productService.getAllProductVendorIdByRange(vendorId,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
//
//    @GetMapping("/productName/{productName}")
//    public ResponseEntity<?> getAllProductByProductName(@PathVariable("productName") String productName,
//                                                          @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
//                                                          @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
//
//        List<Product> products = productService.getAllProductByProductName(productName,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
//
//    @GetMapping("/productNameOrBrandName/{productNameOrBrandName}")
//    public ResponseEntity<?> getAllProductByProductNameOrBrandName(@PathVariable("productNameOrBrandName") String productNameOrBrandName,
//                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
//                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
//
//        List<Product> products = productService.getAllProductByProductNameOrBrandName(productNameOrBrandName,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
//
//    @GetMapping("/brandName/{brandName}")
//    public ResponseEntity<?> getAllProductByBrandName(@PathVariable("brandName") String brandName,
//                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
//                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
//
//        List<Product> products = productService.getAllProductByBrandName(brandName,pageNo,dataLimit);
//
//        return ResponseEntity.ok(products);
//    }
//    @GetMapping("/countAll")
//    public ResponseEntity<?> countProduct(){
//
//        Integer count = productService.countOfAllProduct();
//
//        return ResponseEntity.ok(count);
//    }
//    @GetMapping("/countAllVendorId/{vendorId}")
//    public ResponseEntity<?> countProductByVendorId(@PathVariable String vendorId){
//
//        Integer count = productService.countOfAllProductWithVendorId(vendorId);
//
//        return ResponseEntity.ok(count);
//    }
//
//
//
//    @DeleteMapping("/{productId}")
//    public ResponseEntity<?> deleteProductData(@PathVariable String productId){
//        productService.deleteProductData(productId);
//        return ResponseEntity.ok("Product is deleted");
//    }
//    @GetMapping("/productId/{productId}")
//    public ResponseEntity<?> getProductDataByProductId(@PathVariable String productId){
//        Product product = productService.getProductDataByProductId(productId);
//        return ResponseEntity.ok(product);
//    }
//
//    @GetMapping("/tier/productId/{productId}")
//    public ResponseEntity<?> getProductDataByProductIdWithTierData(@PathVariable String productId){
//
//        Map<String,Object> map = new HashMap<>();
//        List<Tier> tierList = new ArrayList<>();
//        Product product = productService.getProductDataByProductId(productId);
//        map.put("PRODUCT",product);
//        if(product != null){
//            tierList = tierService.getAllTierByTierCode(product.getTierNo());
//            map.put("TIER",tierList);
//        }
//
//        return ResponseEntity.ok(map);
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> createProductDataWithExcel(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("products") String product
//
//    ){
//        Map<String,Object> map = new HashMap<>();
//        if (Helper.checkExcelFormat(file)) {
//            //true
//            Product user = null;
//            try {
//                user = objectMapper.readValue(product,Product.class);
//            } catch (JsonProcessingException e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//                //throw new RuntimeException(e);
//
//            }
//
//            map = productService.saveProductThroughExcel(file,user.getVendorEmail(), user.getVendorId(), user.getVendorName());
//
//            return ResponseEntity.ok(map);
//
//
//        }else {
//            map.put("STATUS","Kindly upload Excel file only");
//        }
//
//        return ResponseEntity.ok(map);
//    }
//
//    @PutMapping("/userUpdate")
//    public ResponseEntity<?> updateUser(
//            @RequestParam("images") MultipartFile images,
//            @RequestParam("userData") String user
//    ){
//        User user1;
//        try {
//            user1 = objectMapper.readValue(user,User.class);
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//            //throw new RuntimeException(e);
//        }
//        Map<String,Object> map;
//        map = userService.updateUser(
//                images,
//                user1.getId()
//        );
//        return ResponseEntity.ok(map);
//    }
//
//
//    // Tier Controller
//
//    @GetMapping("/tier")
//    public ResponseEntity<?> getAllTierData(){
//        List<Tier> tierList = tierService.getAllTier();
//        return ResponseEntity.ok(tierList);
//    }
//
//    @GetMapping("/tier/tierCode/{tierCode}")
//    public ResponseEntity<?> getAllTierData(@PathVariable String tierCode){
//        List<Tier> tierList = tierService.getAllTierByTierCode(tierCode);
//        return ResponseEntity.ok(tierList);
//    }
//
//    @PostMapping("/tier/create")
//    public ResponseEntity<?> createTier(@RequestBody Tier tier){
//        return ResponseEntity.ok(tierService.createTier(tier));
//    }
//    @PutMapping("/tier/update")
//    public ResponseEntity<?> updateTier(@RequestBody Tier tier){
//        return ResponseEntity.ok(tierService.updateTier(tier));
//    }
//
//    @PostMapping("/tier/delete/{id}")
//    public ResponseEntity<?> deleteTier(@PathVariable String id){
//        tierService.deleteTier(id);
//        return ResponseEntity.ok("Done");
//
//    }
//
//
//    // variation
//    @GetMapping("/variationData/{variationName}")
//    public ResponseEntity<?> createVariation(@PathVariable String variationName){
//        Variation variation = new Variation();
//        variation.setVariationName(variationName);
//        return ResponseEntity.ok(variationRepository.save(variation));
//    }
//
//    @GetMapping("/variationData/getAll")
//    public ResponseEntity<?> getAllVariation(){
//        List<Variation> variationList = variationRepository.findAll();
//        return ResponseEntity.ok(variationList);
//    }
//
//    // brand
//    @PostMapping("/brand")
//    public ResponseEntity<?> createBrand(Brand brand){
//        return ResponseEntity.ok(brandRepository.save(brand));
//    }
//
//    @GetMapping("/brand")
//    public ResponseEntity<?> getAllBrand(){
//        return ResponseEntity.ok(brandRepository.findAll());
//    }
//
//
//
//}
