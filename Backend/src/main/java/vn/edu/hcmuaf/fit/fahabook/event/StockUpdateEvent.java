package vn.edu.hcmuaf.fit.fahabook.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.StockAction;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockUpdateEvent extends ApplicationEvent {
    UUID productId;
    int quantity;
    StockAction action;

    public StockUpdateEvent(Object source, UUID productId, int quantity, StockAction action) {
        super(source);
        this.productId = productId;
        this.quantity = quantity;
        this.action = action;
    }
}
