package com.ms_product.product.product;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(

        @NotNull(message = "Product is mandatory")
        Integer productId,
        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
