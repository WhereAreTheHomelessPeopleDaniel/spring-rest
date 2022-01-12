package com.etnetera.hr.controller;

import com.etnetera.hr.exception.NotFoundException;
import com.etnetera.hr.services.JSFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;
	private final JSFService service;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository, JSFService service) {
		this.repository = repository;
		this.service = service;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@GetMapping("/frameworks/{id}")
	public ResponseEntity<JavaScriptFramework> getJSFrameworkById(@PathVariable(value = "id") Long id) throws NotFoundException {
		JavaScriptFramework jsf = repository.findById(id).orElseThrow(() -> new NotFoundException("JavaScriptFramework with id = " + id +" not found"));
		return ResponseEntity.ok().body(jsf);
	}

	@PostMapping("/frameworks")
	public ResponseEntity<JavaScriptFramework> createJSFramework(@Valid @RequestBody JavaScriptFramework jsf){
		return ResponseEntity.ok(repository.save(jsf));

	}

	@PutMapping("/frameworks/{id}")
	public ResponseEntity<JavaScriptFramework> updateJSFramework(@PathVariable(value = "id") Long id, @Valid @RequestBody JavaScriptFramework newjsf) throws NotFoundException {
		JavaScriptFramework jsf = repository.findById(id).orElseThrow(() -> new NotFoundException("JavaScriptFramework with id = " + id +" not found"));
		jsf.setName(newjsf.getName());
		jsf.setDeprecationDate(newjsf.getDeprecationDate());
		jsf.setVersion(newjsf.getVersion());
		jsf.setHypeLevel(newjsf.getHypeLevel());

		return ResponseEntity.ok(repository.save(jsf));
	}

	@DeleteMapping("/frameworks/{id}")
	public ResponseEntity<Long> deleteJSFramework(@PathVariable(value = "id") Long id) throws NotFoundException {
		JavaScriptFramework jsf = repository.findById(id).orElseThrow(() -> new NotFoundException("JavaScriptFramework with id = " + id +" not found"));
		repository.delete(jsf);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	@GetMapping("/frameworks/search")
	public List<JavaScriptFramework> getJSFrameworksByName(@RequestParam String name){
		List<JavaScriptFramework> filtered = service.findByName(name);

		return filtered;
	}


}
