package cn.cow.hotel.service;

import cn.cow.hotel.response.AjaxResult;

public interface CheckInService {

    AjaxResult checkIn(int orderId);

    AjaxResult checkOut(int orderId);

}
