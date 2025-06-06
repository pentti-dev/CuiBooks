package com.example.mobileapi.event;

import com.example.mobileapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final ProductService productService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUpdateStock(StockUpdateEvent event) {
        productService.checkQuantityAvailability(event.getProductId(), event.getQuantity(), event.getAction());
    }

}
