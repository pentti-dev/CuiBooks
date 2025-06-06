package vn.edu.hcmuaf.fit.fahabook.event;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CartRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.CartService;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerCreatedListener {
    CartService cartService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void hanldeCustomerCreate(CustomerCreatedEvent customerCreatedEvent) throws AppException {

        UUID customerId = customerCreatedEvent.getCustomerId();
        cartService.saveCart(CartRequestDTO.builder().customerId(customerId).build());
        log.info("Created cart for  {}", customerId);
    }
}
