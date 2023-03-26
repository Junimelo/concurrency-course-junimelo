package course.concurrency.m3_shared.immutable;

import java.util.List;

public final class Order {

    public enum Status { NEW, IN_PROGRESS, DELIVERED }

    private final Long id;
    private final List<Item> items;
    private final PaymentInfo paymentInfo;
    private final boolean isPacked;
    private final Status status;

    public Order(Long id, List<Item> items, PaymentInfo paymentInfo, boolean isPacked, Status status) {
        this.id = id;
        this.items = items;
        this.paymentInfo = paymentInfo;
        this.isPacked = isPacked;
        this.status = status;
    }

    public synchronized boolean checkStatus() {
        if (items != null && !items.isEmpty() && paymentInfo != null && isPacked) {
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public Status getStatus() {
        return status;
    }

    public static class Builder {
        private Long id;
        private List<Item> items;
        private PaymentInfo paymentInfo;
        private boolean isPacked;
        private Status status;


        public Builder setAllFromOrder(Order order) {
            this.id = order.id;
            this.items = order.items;
            this.paymentInfo = order.paymentInfo;
            this.isPacked = order.isPacked;
            this.status = order.status;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public Builder setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
            return this;
        }

        public Builder setPacked(boolean packed) {
            isPacked = packed;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public synchronized Order build() {
            return new Order(this.id, this.items, this.paymentInfo, this.isPacked, this.status);
        }
    }
}
