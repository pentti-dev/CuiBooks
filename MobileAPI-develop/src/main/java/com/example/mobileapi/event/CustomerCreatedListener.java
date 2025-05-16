package com.example.mobileapi.event;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerCreatedListener {
    CartService cartService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void hanldeCustomerCreate(CustomerCreatedEvent customerCreatedEvent) throws AppException {

        UUID customerId = customerCreatedEvent.getCustomerId();
        log.info("⏳ Đang tạo giỏ hàng cho customer {}", customerId);
        log.info(customerId.toString());
        cartService.saveCart(CartRequestDTO.builder().customerId(customerId).build());
        log.info("✅ Giỏ hàng đã được tạo cho customer {}", customerId);
    }


}
