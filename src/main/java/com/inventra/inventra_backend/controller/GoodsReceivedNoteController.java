package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteResponse;
import com.inventra.inventra_backend.service.GoodsReceivedNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grns")
public class GoodsReceivedNoteController {

    private final GoodsReceivedNoteService service;

    public GoodsReceivedNoteController(GoodsReceivedNoteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoodsReceivedNoteResponse create(@RequestBody GoodsReceivedNoteRequest request) {
        return service.create(request);
    }

    @GetMapping("/{grnId}")
    public GoodsReceivedNoteResponse getById(@PathVariable String grnId) {
        return service.getById(grnId);
    }

    @GetMapping
    public List<GoodsReceivedNoteResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public List<GoodsReceivedNoteResponse> getByPurchaseOrder(@PathVariable String purchaseOrderId) {
        return service.getByPurchaseOrder(purchaseOrderId);
    }

    @GetMapping("/user/{userId}")
    public List<GoodsReceivedNoteResponse> getByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    @PutMapping("/{grnId}")
    public GoodsReceivedNoteResponse update(
            @PathVariable String grnId,
            @RequestBody GoodsReceivedNoteRequest request) {

        return service.update(grnId, request);
    }

    @PatchMapping("/{grnId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable String grnId) {
        service.activate(grnId);
    }

    @PatchMapping("/{grnId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable String grnId) {
        service.deactivate(grnId);
    }

    @DeleteMapping("/{grnId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String grnId) {
        service.delete(grnId);
    }
}
