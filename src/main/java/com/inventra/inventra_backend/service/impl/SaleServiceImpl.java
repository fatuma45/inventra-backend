package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.SaleRequest;
import com.inventra.inventra_backend.dto.response.SaleResponse;
import com.inventra.inventra_backend.entity.Sale;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.mapper.SaleMapper;
import com.inventra.inventra_backend.repository.SaleRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public SaleServiceImpl(
            SaleRepository saleRepository,
            UserRepository userRepository) {

        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SaleResponse create(SaleRequest request) {

        User soldBy = userRepository.findById(request.getSoldBy())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Sale sale = SaleMapper.toEntity(request);

        sale.setSaleId(String.format("SAL%03d", saleRepository.count() + 1));

        sale.setSoldBy(soldBy);

        sale.setTotalAmount(BigDecimal.ZERO);

        saleRepository.save(sale);

        return SaleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse getById(String saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale not found"));

        return SaleMapper.toResponse(sale);
    }

    @Override
    public List<SaleResponse> getAll() {

        return saleRepository.findByDeletedFalse()
                .stream()
                .map(SaleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponse update(String saleId, SaleRequest request) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale not found"));

        User soldBy = userRepository.findById(request.getSoldBy())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        sale.setCustomerName(request.getCustomerName());
        sale.setCustomerPhone(request.getCustomerPhone());
        sale.setSaleDate(request.getSaleDate());
        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setPaymentStatus(request.getPaymentStatus());
        sale.setRemarks(request.getRemarks());
        sale.setSoldBy(soldBy);

        saleRepository.save(sale);

        return SaleMapper.toResponse(sale);
    }

    @Override
    public void delete(String saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale not found"));

        sale.setDeleted(true);

        saleRepository.save(sale);
    }

}
