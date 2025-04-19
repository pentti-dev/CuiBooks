package com.example.mobileapi.event;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerCreatedListener {
    CartService cartService;

    @Async
    @EventListener
    public void hanldeCustomerCreate(CustomerCreatedEvent customerCreatedEvent) {

        Integer customerId = customerCreatedEvent.getCustomerId();
        log.info("⏳ Đang tạo giỏ hàng cho customer {}", customerId);
        cartService.saveCart(CartRequestDTO.builder().customerId(customerId).build());
        log.info("✅ Giỏ hàng đã được tạo cho customer {}", customerId);
    }


}
