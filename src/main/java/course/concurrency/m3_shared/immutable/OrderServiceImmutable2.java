package course.concurrency.m3_shared.immutable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderServiceImmutable2 {

    // Here we don't use AtomicReference and update values inside merge function
    // when contention for value is low it's ok to use synchronized
    private final ConcurrentHashMap<Long, OrderImmutable> currentOrders = new ConcurrentHashMap<>();

    public long createOrder(List<Item> items) {
        OrderImmutable order = new OrderImmutable(items);
        currentOrders.put(order.getId(), order);
        return order.getId();
    }

    public void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        OrderImmutable paid = currentOrders.compute(orderId, (key, o) -> o.withPaymentInfo(paymentInfo));

        // remember that main profit of immutable variables is locality
        if (paid.checkStatus()) {
            deliver(paid);
        }
    }

    public void setPacked(long orderId) {
        OrderImmutable packed = currentOrders.compute(orderId, (key, o) -> o.doPack());

        if (packed.checkStatus()) {
            deliver(packed);
        }
    }

    private void deliver(OrderImmutable order) {
        /* ... */
        currentOrders.compute(order.getId(), (key, o) -> o.withStatus(OrderImmutable.Status.DELIVERED));

    }

    public boolean isDelivered(long orderId) {
        return currentOrders.get(orderId).getStatus().equals(OrderImmutable.Status.DELIVERED);
    }
}
