package vn.edu.hcmuaf.fit.fahabook.event;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.service.CartItemService;
import vn.edu.hcmuaf.fit.fahabook.service.CartService;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RemoveCartListener {
    CartItemService cartItemService;
    CartService cartService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleRemoveCart(RemoveCartEvent removeCartEvent) {
        UUID cartId =
                cartService.getCartByCustomerId(removeCartEvent.getCustomerId()).getId();
        cartItemService.deleteCartItemByCartId(cartId);
    }
}
