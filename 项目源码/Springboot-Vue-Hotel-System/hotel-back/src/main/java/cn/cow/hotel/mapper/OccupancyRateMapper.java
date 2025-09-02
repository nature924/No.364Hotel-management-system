package cn.cow.hotel.mapper;

import cn.cow.hotel.entity.OccupancyRate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OccupancyRateMapper {
    List<OccupancyRate> getOccupancyRates();
}
