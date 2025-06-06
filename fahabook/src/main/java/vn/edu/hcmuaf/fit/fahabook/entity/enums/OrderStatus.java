package vn.edu.hcmuaf.fit.fahabook.entity.enums;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    PENDING,
    PENDING_PAYMENT,
    SHIPPING,
    CARRIER_CANCELLED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    DELIVERED,
    CANCELLED;

    private Set<OrderStatus> nextStatus;

    static {
        PENDING.nextStatus = EnumSet.of(SHIPPING, PENDING_PAYMENT, CANCELLED);
        PENDING_PAYMENT.nextStatus = EnumSet.of(PAYMENT_SUCCESS, PAYMENT_FAILED, CANCELLED);
        SHIPPING.nextStatus = EnumSet.of(DELIVERED, CARRIER_CANCELLED);
        CARRIER_CANCELLED.nextStatus = EnumSet.of(CANCELLED);
        PAYMENT_SUCCESS.nextStatus = EnumSet.of(SHIPPING, DELIVERED, CANCELLED);
        PAYMENT_FAILED.nextStatus = EnumSet.of(PENDING_PAYMENT, CANCELLED);
        DELIVERED.nextStatus = EnumSet.noneOf(OrderStatus.class);
        CANCELLED.nextStatus = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canChangeTo(OrderStatus next) {
        return nextStatus.contains(next);
    }

    public Set<OrderStatus> nextStatus() {
        return Collections.unmodifiableSet(nextStatus);
    }
}
