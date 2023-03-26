package course.concurrency.m3_shared.immutable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private Map<Long, Order> currentOrders = new HashMap<>();
    private long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    public synchronized long createOrder(List<Item> items) {
        long id = nextId();
        Order order = new Order.Builder().setId(id).setItems(items).setStatus(Order.Status.NEW).build();
        currentOrders.put(id, order);
        return id;
    }

    public synchronized void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        Order current = currentOrders.get(orderId);
        current = new Order.Builder().setAllFromOrder(current).setPaymentInfo(paymentInfo).build();
        currentOrders.put(orderId, current);
        if (currentOrders.get(orderId).checkStatus()) {
            deliver(currentOrders.get(orderId));
        }
    }

    public synchronized void setPacked(long orderId) {
        Order current = currentOrders.get(orderId);
        current = new Order.Builder().setAllFromOrder(current).setPacked(true).build();
        currentOrders.put(orderId, current);
        if (currentOrders.get(orderId).checkStatus()) {
            deliver(currentOrders.get(orderId));
        }
    }

    private synchronized void deliver(Order order) {
        /* ... */
        Order current = currentOrders.get(order.getId());
        current = new Order.Builder().setAllFromOrder(current).setStatus(Order.Status.DELIVERED).build();
        currentOrders.put(order.getId(), current);
    }

    public synchronized boolean isDelivered(long orderId) {
        return currentOrders.get(orderId).getStatus().equals(Order.Status.DELIVERED);
    }
}
