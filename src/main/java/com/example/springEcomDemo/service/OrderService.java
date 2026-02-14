package com.example.springEcomDemo.service;

import com.example.springEcomDemo.model.Order;
import com.example.springEcomDemo.model.OrderItem;
import com.example.springEcomDemo.model.Product;
import com.example.springEcomDemo.model.dto.OrderItemRequest;
import com.example.springEcomDemo.model.dto.OrderItemResponse;
import com.example.springEcomDemo.model.dto.OrderRequest;
import com.example.springEcomDemo.model.dto.OrderResponse;
import com.example.springEcomDemo.repo.OrderRepo;
import com.example.springEcomDemo.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    public OrderService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(orderRequest.customerName());
        order.setStatus("PLACED");
        order.setEmail(orderRequest.email());
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemRequest : orderRequest.items()){

            Product product = productRepo.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }

        order.setOrderItem(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItem()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getTotalPrice()
            );
            itemResponses.add(orderItemResponse);
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                itemResponses
        );

        return orderResponse;
    }

    @Transactional
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders){
            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for(OrderItem item : order.getOrderItem()){
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
