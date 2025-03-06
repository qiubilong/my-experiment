package org.example.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.Order;
import org.example.web.dao.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2025/3/6
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    //@DS("write")
    public Order createOder(Long uid, Long orderId) {
        Order entity = new Order();
        entity.setUid(uid);
        entity.setOrderId(orderId);
        orderMapper.insert(entity);
        return entity;
    }
    //@DS("read")
    public List<Order> getOrders(Long uid) {
        return orderMapper.selectList(new QueryWrapper<Order>().lambda().eq(Order::getUid,uid));
    }
}
