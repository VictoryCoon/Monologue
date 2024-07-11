package com.victory.server.mapper;

import com.victory.server.repository.DeviceRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceInfoMapper {
    public List<DeviceRepository> selectDeviceInfo();
}
