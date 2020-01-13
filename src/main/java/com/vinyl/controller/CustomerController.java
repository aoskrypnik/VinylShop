package com.vinyl.controller;

import com.vinyl.model.Customer;
import com.vinyl.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Resource
	private CustomerService customerService;

	@GetMapping
	public List<Customer> findAllCustomers() {
		return customerService.getAll();
	}

	@GetMapping("/{num}")
	public ResponseEntity<?> getCustomerByNum(@PathVariable Integer num) {
		Customer foundCustomer = customerService.getCustomerByNum(num);
		if (isNull(foundCustomer)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundCustomer);
	}

	@PostMapping
	public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
		int generatedKey = customerService.save(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{num}")
				.buildAndExpand(generatedKey).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{num}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable int num) {
		if (isNull(customerService.getCustomerByNum(num))) {
			return ResponseEntity.notFound().build();
		}
		customerService.updateCustomerByNum(customer, num);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{num}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int num) {
		if (isNull(customerService.getCustomerByNum(num))) {
			return ResponseEntity.notFound().build();
		}
		customerService.deleteByNum(num);
		return ResponseEntity.noContent().build();
	}

}
