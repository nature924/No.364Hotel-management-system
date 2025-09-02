package cn.cow.hotel.service.impl;

import cn.cow.hotel.entity.Order;
import cn.cow.hotel.entity.RoomType;
import cn.cow.hotel.enums.OrderStatus;
import cn.cow.hotel.mapper.CheckInMapper;
import cn.cow.hotel.response.AjaxResult;
import cn.cow.hotel.response.ResponseTool;
import cn.cow.hotel.service.CheckInService;
import cn.cow.hotel.service.OrderService;
import cn.cow.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    private CheckInMapper checkInMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * 入住登记
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public AjaxResult checkIn(int orderId) {
        //修改订单状态为已入住
        Order order = orderService.selectById(orderId);
        if (Objects.isNull(order)){
            return ResponseTool.failed("该号对应的订单不存在");
        }
        order.setOrderStatus(OrderStatus.CHECK_IN.getCode());
        order.setUpdateTime(new Date());
        orderService.update(order);
        return ResponseTool.success("入住成功");
    }

    /**
     * 退房登记
     * @param orderId
     * @return
     */
    @Override
    public AjaxResult checkOut(int  orderId){
        //修改订单状态为已入住
        Order order = orderService.selectById(orderId);
        if (Objects.isNull(order)){
            return ResponseTool.failed("该号对应的订单不存在");
        }
        order.setOrderStatus(OrderStatus.CHECK_OUT.getCode());
        order.setUpdateTime(new Date());
        orderService.update(order);
        //修改剩余房间数+1
        RoomType roomType = roomTypeService.selectById(order.getRoomTypeId());
        roomType.setRest(roomType.getRest()+1);
        roomType.setUpdateTime(new Date());
        roomTypeService.update(roomType);
        return ResponseTool.success("退房成功");
    }
}
