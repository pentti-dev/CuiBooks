package com.example.mobileapi.event;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Getter
public class CustomerCreatedEvent extends ApplicationEvent {

    UUID customerId;

    public CustomerCreatedEvent(Object source, UUID customerId) {
        super(source);
        this.customerId = customerId;
    }
}
