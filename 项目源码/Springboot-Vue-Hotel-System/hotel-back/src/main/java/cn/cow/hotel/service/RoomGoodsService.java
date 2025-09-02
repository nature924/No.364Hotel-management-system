package cn.cow.hotel.service;

import cn.cow.hotel.entity.RoomGoods;

import java.util.List;

/**
 * @author
 * @version 1.0.0
 * @ClassName RoomGoodsService
 * @Description TODO
 * @Date 2023-3-18 23:12
 */
public interface RoomGoodsService {

    RoomGoods getById(Integer id);

    List<RoomGoods> selectAll(String searchData);

    void insert(RoomGoods roomGoods);

    void update(RoomGoods roomGoods);

    void delete(Integer id);

}
