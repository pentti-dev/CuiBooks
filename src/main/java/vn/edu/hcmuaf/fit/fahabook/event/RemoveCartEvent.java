package vn.edu.hcmuaf.fit.fahabook.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Getter
public class RemoveCartEvent extends ApplicationEvent {
    UUID customerId;

    public RemoveCartEvent(Object source, UUID customerId) {
        super(source);
        this.customerId = customerId;
    }
}
