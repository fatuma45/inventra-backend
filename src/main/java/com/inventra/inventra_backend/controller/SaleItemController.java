package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.SaleItemRequest;
import com.inventra.inventra_backend.dto.response.SaleItemResponse;
import com.inventra.inventra_backend.service.SaleItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales/{saleId}/items")
@CrossOrigin(origins = "*")
public class SaleItemController {

    private final SaleItemService saleItemService;

    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleItemResponse create(
            @PathVariable String saleId,
            @RequestBody SaleItemRequest request) {

        return saleItemService.create(saleId, request);
    }

    @GetMapping
    public List<SaleItemResponse> getBySale(
            @PathVariable String saleId) {

        return saleItemService.getBySale(saleId);
    }

    @GetMapping("/{saleItemId}")
    public SaleItemResponse getById(
            @PathVariable String saleItemId) {

        return saleItemService.getById(saleItemId);
    }

    @DeleteMapping("/{saleItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String saleItemId) {

        saleItemService.delete(saleItemId);
    }

    @PutMapping("/{saleItemId}")
    public SaleItemResponse update(
            @PathVariable String saleId,
            @PathVariable String saleItemId,
            @RequestBody SaleItemRequest request) {

        return saleItemService.update(saleId, saleItemId, request);
    }
}