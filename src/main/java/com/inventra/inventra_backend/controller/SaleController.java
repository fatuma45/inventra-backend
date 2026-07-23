package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.SaleRequest;
import com.inventra.inventra_backend.dto.response.SaleResponse;
import com.inventra.inventra_backend.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponse create(@RequestBody SaleRequest request) {
        return saleService.create(request);
    }

    @GetMapping
    public List<SaleResponse> getAll() {
        return saleService.getAll();
    }

    @GetMapping("/{id}")
    public SaleResponse getById(@PathVariable String id) {
        return saleService.getById(id);
    }

    @PutMapping("/{id}")
    public SaleResponse update(
            @PathVariable String id,
            @RequestBody SaleRequest request) {

        return saleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        saleService.delete(id);
    }
}