package com.example.mobileapi.event;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Getter
public class CustomerCreatedEvent extends ApplicationEvent {

    Integer customerId;

    public CustomerCreatedEvent(Object source, Integer customerId) {
        super(source);
        this.customerId = customerId;
    }
}
