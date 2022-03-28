package ru.nsu.vadim.data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class Order<T> {
    private static AtomicInteger completedCount = new AtomicInteger(0);
    private final long id;
    private final Collection<? extends T> items;
    private OrderStatus status;
    private final LocalDateTime time;
    private final long locationCoords;

    public Order(
            long id,
            Collection<? extends T> items,
            OrderStatus status,
            LocalDateTime time,
            long locationCoords) {

        this.id = id;
        this.items = items;
        this.status = status;
        this.time = time;
        this.locationCoords = locationCoords;
    }

    public long getId() {
        return id;
    }

    public Collection<? extends T> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public long getLocationCoords() {
        return locationCoords;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.COMPLETE) {
            completedCount.incrementAndGet();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " "
                + "ID=" + getId() + "  "
                + "STATUS=" + getStatus();
    }

    public static AtomicInteger getCompletedCount() {
        return completedCount;
    }
}
