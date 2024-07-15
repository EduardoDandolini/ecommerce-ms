package com.ms.order.kafka;

import com.ms.order.customer.CustomerResponse;
import com.ms.order.order.PaymentMethod;
import com.ms.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
