package ru.pivovarov.t1.LoggingOperations.service;

import ru.pivovarov.t1.LoggingOperations.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> getOrders();
    Order getOrderById(Long id);
    void deleteOrderById(Long id);
}
