package com.labequiped.productservice.repository;

import com.labequiped.productservice.entities.Tier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TierRepository extends MongoRepository<Tier,String> {

}
