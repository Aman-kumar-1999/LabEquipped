package com.labequiped.productservices.repository;

import com.labequiped.productservices.entities.Variation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationRepository extends MongoRepository<Variation, String> {

}
