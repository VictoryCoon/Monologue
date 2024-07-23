package com.victory.batch.mapper;

import com.victory.batch.repository.DeviceRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceInfoMapper {
    public List<DeviceRepository> selectDeviceInfo();
}
