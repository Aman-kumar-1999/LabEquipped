package com.labequiped.productservice.controller;

import com.labequiped.productservice.entities.Brand;
import com.labequiped.productservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @PostMapping("/create")
    public ResponseEntity<Brand> create(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandRepository.save(brand));
    }

    @GetMapping
    public ResponseEntity<List<Brand>> all() {
        return ResponseEntity.ok(brandRepository.findAll());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProductDataWithUserData(
            @RequestParam("file") MultipartFile file
//            @RequestPart("products") String product

    ){
        return ResponseEntity.ok("ok");

    }
}

