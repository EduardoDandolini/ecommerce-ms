package com.ms_product.product.product;

import com.ms_product.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest productRequest) {
        var product = mapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> purchaseRequest) {
        var productIds = purchaseRequest
                .stream()
                .map(PurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw  new ProductPurchaseException("One or more products not exits");
        }

        var storedRequest = purchaseRequest
                .stream()
                .sorted(Comparator.comparing(PurchaseRequest::productId))
                .toList();

        var purchaseProducts = new ArrayList<PurchaseResponse>();
        for (int i = 0; i < storedRequest.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw  new ProductPurchaseException("Insufficient stock quantity for product with id:: " + product.getId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchaseProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchaseProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID :: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
