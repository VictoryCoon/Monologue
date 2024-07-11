package com.victory.server.worker;

import com.victory.server.service.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceInfoSyncWorker implements Runnable{
    private final DeviceInfoService deviceInfoService;

    public DeviceInfoSyncWorker(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Override
    public void run() {
        while (true){
            try {
                deviceInfoService.deviceInfoCaching();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
