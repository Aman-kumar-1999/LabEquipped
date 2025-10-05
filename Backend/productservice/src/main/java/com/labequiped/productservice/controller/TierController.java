package com.labequiped.productservice.controller;


import com.labequiped.productservice.entities.Tier;
import com.labequiped.productservice.repository.TierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tier")
public class TierController {

    @Autowired
    private TierRepository tierRepository;

    @GetMapping
    public ResponseEntity<List<Tier>> getAll() {
        return ResponseEntity.ok(tierRepository.findAll());
    }

    @GetMapping("/tierCode/{tierCode}")
    public ResponseEntity<Tier> getByCode(@PathVariable String tierCode) {
        return tierRepository.findAll().stream()
                .filter(t -> tierCode.equals(t.getTierCode()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Tier> create(@RequestBody Tier tier) {
        return ResponseEntity.ok(tierRepository.save(tier));
    }

    @PutMapping("/update")
    public ResponseEntity<Tier> update(@RequestBody Tier tier) {
        return ResponseEntity.ok(tierRepository.save(tier));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        tierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
