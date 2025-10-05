package com.labequiped.productservice.repository;

import com.labequiped.productservice.entities.Variation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationRepository extends MongoRepository<Variation, String> {

}
