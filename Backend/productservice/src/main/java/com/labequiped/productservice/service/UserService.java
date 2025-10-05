package com.labequiped.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


//@Service
public interface UserService {
	
	// update User Details
	public Map<String, Object> updateUser(MultipartFile images, String id);


}
