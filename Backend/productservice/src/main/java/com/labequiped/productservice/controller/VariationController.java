package com.labequiped.productservice.controller;

import com.labequiped.productservice.entities.Variation;
import com.labequiped.productservice.repository.VariationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/variationData")
public class VariationController {
    private VariationRepository variationRepository;

    @GetMapping("/{variationName}")
    public ResponseEntity<List<Variation>> getByName(@PathVariable String variationName) {
        List<Variation> list = variationRepository.findAll().stream()
                .filter(v -> variationName.equalsIgnoreCase(v.getVariationName()))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Variation>> getAll() {
        return ResponseEntity.ok(variationRepository.findAll());
    }
}

