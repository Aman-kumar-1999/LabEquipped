package com.labequiped.productservices.services;

import com.labequiped.productservices.entities.Tier;

import java.util.List;


public interface TierService {

    // Get all Tier Service
    public List<Tier> getAllTier();

    // get all Tier By TireCode
    public List<Tier> getAllTierByTierCode(String tierCode);

    // Create Tier
    public Tier createTier(Tier tier);

    // update Tier
    public Tier updateTier(Tier tier);

    // delete Tier
    public void deleteTier(String id);

}
