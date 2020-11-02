package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
    Order getOrdger(Long id);
}
