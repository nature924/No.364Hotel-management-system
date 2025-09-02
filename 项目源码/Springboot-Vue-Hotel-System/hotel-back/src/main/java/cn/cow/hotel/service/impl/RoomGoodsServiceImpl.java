package cn.cow.hotel.service.impl;

import cn.cow.hotel.entity.RoomGoods;
import cn.cow.hotel.mapper.RoomGoodsMapper;
import cn.cow.hotel.service.RoomGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @version 1.0
 * @ClassName RoomGoodsServiceImpl
 * @Description TODO
 * @createTime 2023-3-18 23:13
 */
@Service
public class RoomGoodsServiceImpl implements RoomGoodsService {

    @Autowired
    private RoomGoodsMapper roomGoodsMapper;

    @Override
    public RoomGoods getById(Integer id) {
        return roomGoodsMapper.selectById(id);
    }

    @Override
    public List<RoomGoods> selectAll(String searchData) {
        QueryWrapper<RoomGoods> queryWrapper=new QueryWrapper<>();
        if (StringUtils.hasLength(searchData)){
            queryWrapper.like("name",searchData);
        }
        return roomGoodsMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(RoomGoods roomGoods) {
        roomGoods.setCreateTime(new Date());
        roomGoods.setUpdateTime(new Date());
        roomGoodsMapper.insert(roomGoods);
    }

    @Override
    public void update(RoomGoods roomGoods) {
        roomGoods.setUpdateTime(new Date());
        roomGoodsMapper.updateById(roomGoods);
    }

    @Override
    public void delete(Integer id) {
        roomGoodsMapper.deleteById(id);
    }
}
