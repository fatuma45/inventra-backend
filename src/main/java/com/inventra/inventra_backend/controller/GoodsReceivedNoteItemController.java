package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteItemRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteItemResponse;
import com.inventra.inventra_backend.service.GoodsReceivedNoteItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grn-items")
public class GoodsReceivedNoteItemController {

    private final GoodsReceivedNoteItemService service;

    public GoodsReceivedNoteItemController(GoodsReceivedNoteItemService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoodsReceivedNoteItemResponse create(@RequestBody GoodsReceivedNoteItemRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public GoodsReceivedNoteItemResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public List<GoodsReceivedNoteItemResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/grn/{grnId}")
    public List<GoodsReceivedNoteItemResponse> getByGrn(@PathVariable String grnId) {
        return service.getByGrn(grnId);
    }

    @GetMapping("/product/{productId}")
    public List<GoodsReceivedNoteItemResponse> getByProduct(@PathVariable String productId) {
        return service.getByProduct(productId);
    }

    @PutMapping("/{id}")
    public GoodsReceivedNoteItemResponse update(
            @PathVariable String id,
            @RequestBody GoodsReceivedNoteItemRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}