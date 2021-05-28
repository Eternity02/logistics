package com.example.api.service.impl;

import com.example.api.model.entity.Commodity;
import com.example.api.model.entity.Distribution;
import com.example.api.model.entity.Driver;
import com.example.api.model.entity.Vehicle;
import com.example.api.repository.*;
import com.example.api.service.DistributionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Resource
    private DistributionRepository distributionRepository;

    @Resource
    private DriverRepository driverRepository;

    @Resource
    private VehicleRepository vehicleRepository;

    @Resource
    private CommodityRepository commodityRepository;

    @Resource
    private SaleRepository saleRepository;

    @Override
    public Distribution save(Distribution distribution) throws Exception {
        if (distributionRepository.findById(distribution.getId()).isPresent()) {
            Optional<Driver> driver = driverRepository.findById(distribution.getDid());
            Optional<Vehicle> vehicle = vehicleRepository.findById(distribution.getVid());
            Commodity commodity = commodityRepository.findByName(distribution.getCommodity());
            if (!driver.isPresent() || !vehicle.isPresent()) throw new Exception("请求参数错误");
            if (distribution.getStatus() != 2) {
                if (driver.get().isDriving() || vehicle.get().isDriving()) throw new Exception("司机或货车状态不可用");
            }
            if (distribution.getStatus() == 1) {
                driverRepository.updateDriving(true, distribution.getDid());
                vehicleRepository.updateDriving(true, distribution.getVid());
                saleRepository.updateDriving(true, distribution.getSid());
                commodity.setCount(commodity.getCount() - distribution.getCount());
                commodityRepository.save(commodity);

            } else {
                saleRepository.updateDriving(false, distribution.getSid());
                driverRepository.updateDriving(false, distribution.getDid());
                vehicleRepository.updateDriving(false, distribution.getVid());

            }


        }
        return distributionRepository.save(distribution);
    }

    @Override
    public List<Distribution> findAll() {
        return distributionRepository.findAll();
    }

}
