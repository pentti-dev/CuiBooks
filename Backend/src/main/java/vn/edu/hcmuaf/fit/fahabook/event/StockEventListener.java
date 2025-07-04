package vn.edu.hcmuaf.fit.fahabook.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.service.ProductService;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final ProductService productService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUpdateStock(StockUpdateEvent event) {
        productService.checkQuantityAvailability(event.getProductId(), event.getQuantity(), event.getAction());
    }
}
