package com.example.mobileapi.event;

import com.example.mobileapi.entity.enums.StockAction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

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
