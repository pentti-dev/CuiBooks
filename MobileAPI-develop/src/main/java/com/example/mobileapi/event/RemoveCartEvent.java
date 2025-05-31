package com.example.mobileapi.event;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Getter
public class RemoveCartEvent extends ApplicationEvent {
    UUID customerId;

    public RemoveCartEvent(Object source, UUID customerId) {
        super(source);
        this.customerId = customerId;
    }
}
