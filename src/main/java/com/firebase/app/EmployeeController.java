package com.firebase.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@PostMapping
	public String save(@RequestBody Employee employee) {
		return service.save(employee);
	}
	
	@GetMapping("/{id}")
	public Employee getById(@PathVariable Integer id) {
		return service.getById(id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Integer id) {
		return service.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public String updateById(@PathVariable Integer id, @RequestBody Employee employee) {
		return service.update(id, employee);
	}
	
	@GetMapping
	public List<Employee> getAll(){
		return service.getAll();
	}
}
