package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.config.IntegrationPurchaseConfig;
import geekbrains.home.des.springleveltwo.dao.OrderDAO;
import geekbrains.home.des.springleveltwo.domain.Order;
import geekbrains.home.des.springleveltwo.domain.OrderDetail;
import geekbrains.home.des.springleveltwo.domain.integration.Purchase;
import geekbrains.home.des.springleveltwo.domain.integration.Sell;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final IntegrationPurchaseConfig purchaseConfig;

    public OrderServiceImpl(OrderDAO orderDAO, IntegrationPurchaseConfig purchaseConfig) {
        this.orderDAO = orderDAO;
        this.purchaseConfig = purchaseConfig;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderDAO.save(order);
        sendIntegrationPurchaseNotify(order);
    }

    private void sendIntegrationPurchaseNotify(Order order) {
        Purchase purchase = new Purchase();
        List<Sell> sells = new ArrayList<>();
        for (OrderDetail d : order.getDetails()) {
            sells.add(Sell.builder()
                    .username(order.getUser().getName())
                    .orderID(order.getId())
                    .productTitle(d.getProduct().getTitle())
                    .amount(d.getAmount().longValue())
                    .sum(d.getAmount().multiply(d.getPrice()))
                    .build());
        }
        purchase.setSells(sells);

        Message<Purchase> message = MessageBuilder.withPayload(purchase)
                .setHeader("Content-type", "application/json")
                .build();

        purchaseConfig.getPurchaseChannel().send(message);

    }

    @Override
    public Order getOrdger(Long id) {
        return orderDAO.findById(id).orElse(null);
    }
}
