package com.example.retail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.retail.entity.Category;
import com.example.retail.service.CategoryService;

@RestController

@RequestMapping("/category")
public class CategoryController{
	
	@Autowired
	private CategoryService catservice;
	
	@PostMapping("/add")
	public ResponseEntity<?>categoryadd(@RequestBody Category cat){		
		return catservice.categoryadd(cat);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/all")
	public ResponseEntity<?>selectall(){
		
		return catservice.selectall();
	}
	
	
	  @GetMapping("/catall")
	    public Page<Category> getEmployees(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {
	        return catservice.getCategory(page, size);
	    }
	
	 private Map<Long, Category> catDatabase = new HashMap<>();
	 @PutMapping("/update/{id}")
	    public ResponseEntity<Category> updatePerson(@PathVariable Long id, @Validated @RequestBody Category cat) {
	        // Check if the person exists
	
	        if (!catDatabase.containsKey(id)) {
	            return ResponseEntity.notFound().build();  // Return 404 if not found
	        }
	        // Update the person
	        Category existingCat = catDatabase.get(id);	        
	        existingCat.setCat_name(cat.getCat_name());
	        existingCat.setCatDescription(cat.getCatDescription());

	        return ResponseEntity.ok(existingCat);  // Return the updated person with status 200 OK
	    }
	


	    // Get a person by ID
	    @GetMapping("/get/{id}")
	    public ResponseEntity<Category> getCatById(@PathVariable Long id) {
	        // Retrieve the person from the mock database
	    	Category person = catDatabase.get(id);
	        
	        // If person is not found, return a 404 Not Found
	        if (person == null) {
	            return ResponseEntity.notFound().build();  // HTTP 404
	        }
	        
	        // Return the person with HTTP 200 OK
	        return ResponseEntity.ok(person);  // HTTP 200
	    }
	
	@GetMapping("/get")
	public String getAllStudents() {
		ModelMap mav = new ModelMap();
		List<Category> studentList = catservice.getAll();
		mav.addAttribute("students", studentList);
	    return "index";
	
	}
	
}