package com.labequiped.productservices.services.impl;
//import com.labequiped.productservice.service.kafka.KafkaProducerService;
import com.labequiped.productservices.entities.Product;

import com.labequiped.productservices.repository.ProductRepository;
import com.labequiped.productservices.services.ProductService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

//    @Autowired
//    private ProductRepository productRepository;
//
//
//    @Autowired
//    private FileUpload fileUpload;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

//    @Autowired
//    private KafkaProducerService kafkaProducerService;

    private static final String CACHE = "products";

    public Page<Product> findAllPaged(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    @Cacheable(value = CACHE, key = "#id")
    public Optional<Product> getById(String id) {
        return productRepository.findById(id);
    }

    @Cacheable(value = CACHE, key = "'all_'+#page+'_'+#size+'_'+#sortBy+'_'+#sortDir")
    public Page<Product> getAllCached(int page, int size, String sortBy, String sortDir) {
        return findAllPaged(page, size, sortBy, sortDir);
    }

    @CachePut(value = CACHE, key = "#product.productId")
    public Product createOrUpdate(Product product) {
        Product saved = productRepository.save(product);
        // Optionally publish an event so other services pick up changes
//        kafkaProducerService.publish("productSaved", saved);
        return saved;
    }

    private GridFSBucket getGridFsBucket() {
        return GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }

    public Product saveProduct(Product product, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            GridFSBucket gridFSBucket = getGridFsBucket();
            try (InputStream inputStream = image.getInputStream()) {
                ObjectId fileId = gridFSBucket.uploadFromStream(image.getOriginalFilename(), inputStream);
                product.setImageFileId(fileId.toHexString());
                product.setImageName(image.getOriginalFilename());
            }
        }
        product.setCreatedAt(LocalDate.now());
        return productRepository.save(product);
    }

    public byte[] getProductImage(String fileId) throws IOException {
        GridFSBucket gridFSBucket = getGridFsBucket();
        GridFSFile gridFSFile = gridFSBucket.find(new org.bson.Document("_id", new ObjectId(fileId))).first();

        if (gridFSFile != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridFSBucket.downloadToStream(gridFSFile.getObjectId(), outputStream);
            return outputStream.toByteArray();
        }
        return null;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = CACHE, key = "#id")
    public void delete(String id) {
        productRepository.deleteById(id);
//        kafkaProducerService.publish("productDeleted", id);
    }

    // Filter helpers (all paged)
    public Page<Product> filterByProductName(String productName, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCase(productName, pageable);
    }

    public Page<Product> filterByCategory(String category, Pageable pageable) {
        return productRepository.findByCategoryIgnoreCase(category, pageable);
    }

    public Page<Product> filterByVariationId(String variationId, Pageable pageable) {
        return productRepository.findByVariationId(variationId, pageable);
    }

    public Page<Product> filterByVendorId(String vendorId, Pageable pageable) {
        return productRepository.findByVendorId(vendorId, pageable);
    }

    public Page<Product> filterByBrandName(String brandName, Pageable pageable) {
        return productRepository.findByBrandNameContainingIgnoreCase(brandName, pageable);
    }

    public Page<Product> productNameOrBrand(String term, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCaseOrBrandNameContainingIgnoreCase(term, term, pageable);
    }

    public long countAll() {
        return productRepository.count();
    }

    public long countByVendorId(String vendorId) {
        return productRepository.countByVendorId(vendorId);
    }


////    @CachePut(value = "products", key = "#productId")
//    public Map<String, Object> createProductData(
//            MultipartFile images,
//            String vendorId,
//            String vendorName,
//            String brandName,
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
//
//    ){
//
//
//        Map<String,Object> map = new HashMap<>();
//
//        if (!images.isEmpty()) {
//            try {
//                Map<String,Object> uploadMap  = fileUpload.uploadFile(images);
//                map.put("File Exception: ",uploadMap.get("e"));
//                boolean uploaded = (boolean) uploadMap.get("f");
//                if(uploaded) {
//                    Product product = new Product();
//                    product.setVendorId(vendorId);
//                    product.setVendorName(vendorName);
//                    product.setVendorEmail(vendorEmail);
//                    product.setProductName(productName);
//                    product.setBrandName(brandName);
//                    product.setProductDescription(productDescription);
//                    product.setCategory(category);
//                    product.setProductQuantity(productQuantity);
//                    product.setIndividualProductPrice(individualProductPrice);
//                    float discountPrice = (discountPercentage / 100) * individualProductPrice;
//                    float gst1 = (gst /100) * individualProductPrice;
//                    float natePriceWithDiscount = individualProductPrice - discountPrice;
//                    product.setNatePriceWithDiscount(natePriceWithDiscount);
//                    float totalProductPrice;
//                    totalProductPrice = natePriceWithDiscount * productQuantity + gst1;
//                    product.setTotalProductPrice(totalProductPrice);
//                    product.setStatus(status);
//                    product.setAction(action);
//                    product.setDate(LocalDate.now());
//                    product.setImageName(uploadMap.get("fileName").toString());
//                    product.setImagePath(uploadMap.get("filePath").toString());
//                    product.setDiscountPercentage(discountPercentage);
//                    product.setBulkCode(bulkCode);
//                    product.setVariationName(variationName);
//                    product.setVariationId(variationId);
//                    product.setGst(gst);
//                    product.setHsn(hsn);
//                    product.setIsVerified(isVerified);
//                    product.setTierNo(tierNo);
//                    product.setContainLiquid(containLiquid);
//                    product.setCompanyCode(companyCode);
//                    product.setBulkPack(bulkPack);
//                    product.setBulkPrice(bulkPrice);
//
//                    //float discountPrice = (discountPercentage/100)*totalProductPrice;
//                    //product.setNatePriceWithDiscount(totalProductPrice-discountPrice);
//
//                    productRepository.save(product);
//                    map.put("STATUS", "SUCCESS");
//                    map.put("PRODUCT", product);
//                    //map.put("filePath",uploadMap.get("filePath"));
//                }else
//                    map.put("STATUS","FAILED");
//
//            } catch (Exception e) {
//                map.put("STATUS","InternalServerError");
//                map.put("EXCEPTION",e);
//            }
//        }
//
//        return map;
//    }
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
//    ){
//
//
//        Map<String,Object> map = new HashMap<>();
//
//        if (!images.isEmpty()) {
//            try {
//                Map<String,Object> uploadMap  = fileUpload.uploadFile(images);
//                map.put("File Exception: ",uploadMap.get("e"));
//                boolean uploaded = (boolean) uploadMap.get("f");
//                if(uploaded) {
//                    Product product1 = productRepository.findById(productId).get();
//                    if (product1!=null) {
//                        Product product = new Product();
//                        product.setProductId(productId);
//                        product.setVendorId(vendorId);
//                        product.setVendorName(vendorName);
//                        product.setVendorEmail(vendorEmail);
//                        product.setProductName(productName);
//                        product.setBrandName(brandName);
//                        product.setProductDescription(productDescription);
//                        product.setCategory(category);
//                        product.setProductQuantity(productQuantity);
//                        product.setIndividualProductPrice(individualProductPrice);
//                        float discountPrice = (discountPercentage / 100) * individualProductPrice;
//                        float gst1 = (gst /100) * individualProductPrice;
//                        float natePriceWithDiscount = individualProductPrice - discountPrice;
//                        product.setNatePriceWithDiscount(natePriceWithDiscount);
//                        float totalProductPrice;
//                        totalProductPrice = natePriceWithDiscount * productQuantity + gst1;
//                        product.setTotalProductPrice(totalProductPrice);
//                        product.setStatus(status);
//                        product.setAction(action);
//                        product.setDate(LocalDate.now());
//                        product.setImageName(uploadMap.get("fileName").toString());
//                        product.setImagePath(uploadMap.get("filePath").toString());
//                        product.setDiscountPercentage(discountPercentage);
//                        product.setBulkCode(bulkCode);
//                        product.setVariationName(variationName);
//                        product.setVariationId(variationId);
//                        product.setGst(gst);
//                        product.setHsn(hsn);
//                        product.setIsVerified(isVerified);
//                        product.setTierNo(tierNo);
//                        product.setContainLiquid(containLiquid);
//                        product.setCompanyCode(companyCode);
//                        product.setBulkPack(bulkPack);
//                        product.setBulkPrice(bulkPrice);
//                        //float discountPrice = (discountPercentage / 100) * totalProductPrice;
//                        //product.setNatePriceWithDiscount(totalProductPrice - discountPrice);
//                        product1 = product;
//                        productRepository.save(product1);
//                        map.put("STATUS", "SUCCESS");
//                        map.put("PRODUCT", product);
//                    }else
//                        map.put("STATUS","Product is not Present");
//                    //map.put("filePath",uploadMap.get("filePath"));
//                }else
//                    map.put("STATUS","FAILED");
//
//            } catch (Exception e) {
//                map.put("STATUS","InternalServerError");
//                map.put("EXCEPTION",e);
//            }
//        }
//
//        return map;
//    }
//    public List<Product> getAllProductData(){
//        return productRepository.findAll();
//    }
//
//    public List<Product> getAllProductDataWithRange(int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1,dataLimit1);
//        Page<Product> page = productRepository.findAll(pageable);
//        List<Product> filteredList = page.getContent();
//
////        List<Product> filteredList = new ArrayList<>();
////        int pageNo = pageNo1;
////        int dataLimit = dataLimit1;
////        for (int i=0; i < productList.size(); i++) {
////            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
////                if(productList.get(i)!=null) {
////                    filteredList.add(productList.get(i));
////                    System.out.println("Index : " + i);
////                }
////            }
////        }
//        return filteredList;
//    }
//
//    public List<Product> getAllProductCategoryWithRange(String category, int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("category").is(category));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//        Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//        List<Product>  filteredList =  page.getContent();
//
////        Query query = new Query().with(PageRequest.of(pageNo1,dataLimit1));
////        query.addCriteria(Criteria.where("category").is(category));
////        List<Product> products = mongoTemplate.find(query, Product.class);
////        List<Product> filteredList = new ArrayList<>();
////        int pageNo = pageNo1;
////        int dataLimit = dataLimit1;
////        for (int i=0; i < products.size(); i++) {
////            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
////                if(products.get(i)!=null) {
////                    filteredList.add(products.get(i));
////                    System.out.println("Index : " + i);
////                }
////            }
////        }
//
//        return filteredList;
//    }
//
//    public List<Product> getAllProductCategory(String category){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("category").is(category));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//
//        return products;
//    }
//
//    public List<Product> getAllProductVendorId(String vendorId){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("vendorId").is(vendorId));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//
//        return products;
//    }
//
//    @Override
//    public List<Product> getAllProductVendorIdByRange(String vendorId, int pageNo1, int dataLimit1) {
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("vendorId").is(vendorId));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//         Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//         List<Product>  filteredList =  page.getContent();
////        Query query = new Query();
////        query.addCriteria(Criteria.where("vendorId").is(vendorId));
////        List<Product> products = mongoTemplate.find(query, Product.class);
////        List<Product> filteredList = new ArrayList<>();
////        int pageNo = pageNo1;
////        int dataLimit = dataLimit1;
////        for (int i=0; i < products.size(); i++) {
////            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
////                if(products.get(i)!=null) {
////                    filteredList.add(products.get(i));
////                    System.out.println("Index : " + i);
////                }
////            }
////        }
//
//
//        return filteredList;
//    }
//
//    public List<Product> getAllProductVariationId(String variationId){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("variationId").is(variationId));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//
//        return products;
//    }
//
//    public List<Product> getAllProductVariationIdByRange(String variationId, int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("variationId").is(variationId));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//        Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//        List<Product>  filteredList =  page.getContent();
//
////        Query query = new Query();
////        query.addCriteria(Criteria.where("variationId").is(variationId));
////        List<Product> products = mongoTemplate.find(query, Product.class);
////        List<Product> filteredList = new ArrayList<>();
////        int pageNo = pageNo1;
////        int dataLimit = dataLimit1;
////        for (int i=0; i < products.size(); i++) {
////            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
////                if(products.get(i)!=null) {
////                    filteredList.add(products.get(i));
////                    System.out.println("Index : " + i);
////                }
////            }
////        }
//
//
//        return filteredList;
//    }
//
//    public List<Product> getAllProductByProductName(String productName, int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "productName"));
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("productName").regex("^"+productName));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//        Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//        List<Product>  filteredList =  page.getContent();
//
//        return filteredList;
//    }
//
//    public List<Product> getAllProductByProductNameOrBrandName(String productName, int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "productName"));
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("").orOperator(Criteria.where("productName").regex("^"+productName),Criteria.where("brandName").regex("^"+productName)));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//        Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//        List<Product>  filteredList =  page.getContent();
//
//        return filteredList;
//    }
//
//
//    public List<Product> getAllProductByBrandName(String brandName, int pageNo1, int dataLimit1){
//
//        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "brandName"));
//        Query query = new Query().with(pageable);
//        query.addCriteria(Criteria.where("brandName").is(brandName));
//        List<Product> list = mongoTemplate.find(query, Product.class);
//
//        Page<Product> page = PageableExecutionUtils.getPage(
//                list,
//                pageable,
//                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));
//
//        List<Product>  filteredList =  page.getContent();
//
//        return filteredList;
//    }
//
//    public Integer countOfAllProduct(){
//        return Math.toIntExact(productRepository.count());
//
//    }
//    public Integer countOfAllProductWithVendorId(String vendorId){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("vendorId").is(vendorId));
//        long count = mongoTemplate.count(query, Product.class);
//        return Math.toIntExact(count);
//
//    }
//
//    public String deleteProductData(String productId){
//        productRepository.deleteById(productId);
//        return "Product is deleted";
//    }
//    public Product getProductDataByProductId(String productId){
//        Product product = productRepository.findById(productId).get();
//        return product;
//    }
//
//    @Override
//    public List<Product> getProductByProductName(String productName) {
//        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, productName));
//        return products;
//    }
//
//    public Map<String,Object> saveProductThroughExcel(MultipartFile file, String vendorEmail, String vendorId, String vendorName) {
//
//        Map<String,Object> map = new HashMap<>();
//        try {
//            map=Helper.convertExcelToListOfProduct(file.getInputStream());
//
//            List<Product> products = (List<Product>) map.get("PRODUCTS");
//            //List<Product> products = Helper.convertExcelToListOfProduct(file.getInputStream());
//            if(products!=null || products.isEmpty()) {
//                List<Product> productList = products.stream().map(product -> {
//
//                    float discountPrice = (product.getDiscountPercentage() / 100) * product.getIndividualProductPrice();
//                    float natePriceWithDiscount = product.getIndividualProductPrice() - discountPrice;
//                    product.setNatePriceWithDiscount(natePriceWithDiscount);
//                    float totalProductPrice;
//                    totalProductPrice = natePriceWithDiscount * product.getProductQuantity();
//                    product.setTotalProductPrice(totalProductPrice);
//                    product.setDate(LocalDate.now());
//                    product.setImageName("No");
//                    product.setImagePath("No");
//                    product.setVendorId(vendorId);
//                    product.setVendorName(vendorName);
//                    product.setVendorEmail(vendorEmail);
//
//                    return product;
//                }).collect(Collectors.toList());
//                this.productRepository.saveAll(productList);
//                map.put("MSG","Product has been created Successful");
//                map.put("STATUS","SUCCESS");
//                map.put("PRODUCTS",productList);
//            }else {
//                map.put("STATUS","FAILED");
//                map.put("MSG","Cause exception");
//                map.put("PRODUCTS","NULL");
//            }
//
//        } catch (Exception e) {
//            map.put("STATUS","FAILED");
//            map.put("PRODUCTS","NULL");
//            e.printStackTrace();
//        }
//        return map;
//    }
}
