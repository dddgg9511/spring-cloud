package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Orders;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
        Orders orders = modelMapper.map(orderDto, Orders.class);

        Orders saveOrder = orderRepository.save(orders);

        return modelMapper.map(saveOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Orders orders = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("not found order."));

        OrderDto orderDto = modelMapper.map(orders, OrderDto.class);
        return orderDto;

    }

    @Override
    public Iterable<Orders> getOrderByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
