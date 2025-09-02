package cn.cow.hotel.controller.worker;

import cn.cow.hotel.response.AjaxResult;
import cn.cow.hotel.service.CheckInService;
import cn.cow.hotel.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("订单检查控制器")
@RestController
@RequestMapping(value = "/op/checkIn")
public class OpCheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private OrderService orderService;

    @ApiOperation("入驻")
    @RequestMapping(value = "/in")
    public AjaxResult addCheckIn(int orderId){
        return checkInService.checkIn(orderId);
    }

    @ApiOperation("退订")
    @RequestMapping(value = "/out")
    public AjaxResult checkOut(int orderId) {
        return checkInService.checkOut(orderId);
    }


}
