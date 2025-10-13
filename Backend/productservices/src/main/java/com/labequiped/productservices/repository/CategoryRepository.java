package com.labequiped.productservices.repository;


import com.labequiped.productservices.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByCategoryName(String categoryName);
    

}
