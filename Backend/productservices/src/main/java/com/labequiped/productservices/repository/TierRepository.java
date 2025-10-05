package com.labequiped.productservices.repository;

import com.labequiped.productservices.entities.Tier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TierRepository extends MongoRepository<Tier,String> {

}
