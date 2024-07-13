package com.victory.client.mapper;

import com.victory.client.repository.DeviceRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceInfoMapper {
    public List<DeviceRepository> selectDeviceInfo();
}
