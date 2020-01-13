package com.vinyl.controller;

import com.vinyl.model.Salesman;
import com.vinyl.service.SalesmanService;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/salesman")
public class SalesmanController {

    @Resource
    private SalesmanService salesmanService;

    @GetMapping
    public List<Salesman> getAllSalesmen() {
        return salesmanService.getAll();
    }

	//TODO handle exception
    @GetMapping("/{tabNum}")
    public Salesman getSalesmanByTabNum(@PathVariable Integer tabNum) {
        return salesmanService.getSalesmanByTabNum(tabNum);
    }

    //TODO make method save return saved object
    @PostMapping
    public ResponseEntity<Object> saveSalesman(@RequestBody Salesman salesman) {
        int createdSalesmanId = salesmanService.save(salesman);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tabNum}")
                .buildAndExpand(createdSalesmanId).toUri();
        return ResponseEntity.created(location).build();
    }

	@PutMapping("/{tabNum}")
	public ResponseEntity<Object> updateSalesman(@RequestBody Salesman salesman, @PathVariable int tabNum) {
		if (isNull(salesmanService.getSalesmanByTabNum(tabNum))) {
			return ResponseEntity.notFound().build();
		}
		salesmanService.updateSalesmanByTabNum(salesman, tabNum);
		return ResponseEntity.ok().build();
	}

}
