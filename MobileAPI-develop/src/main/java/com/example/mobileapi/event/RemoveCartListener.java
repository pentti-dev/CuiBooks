package com.example.mobileapi.event;

import com.example.mobileapi.service.CartItemService;
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
public class RemoveCartListener {
    CartItemService cartItemService;
    CartService cartService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleRemoveCart(RemoveCartEvent removeCartEvent) {
        UUID cartId = cartService.getCartByCustomerId(removeCartEvent.getCustomerId()).getId();
        cartItemService.deleteCartItemByCartId(cartId);
    }
}
