package com.anuj.service;

import com.anuj.domain.OrderStatus;
//import com.anuj.exception.OrderException;
import com.anuj.model.*;
import com.anuj.model.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart);

    public Order findOrderById(Long orderId) throws Exception;

    public List<Order> usersOrderHistory(Long userId);

    public List<Order> sellersOrder(Long sellerId);

    public Order updateOrderStatus(Long orderId,
                                   OrderStatus orderStatus)
            throws Exception;

    public void deleteOrder(Long orderId) throws Exception;

    Order cancelOrder(Long orderId, User user) throws Exception;

    OrderItem getOrderItemById(Long id) throws Exception;


}