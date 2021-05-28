package com.example.api.controller;

import com.example.api.model.entity.Distribution;
import com.example.api.repository.CommodityRepository;
import com.example.api.repository.DriverRepository;
import com.example.api.repository.SaleRepository;
import com.example.api.repository.VehicleRepository;
import com.example.api.service.DistributionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/distribution")
public class DistributionController {

    @Resource
    private DistributionService distributionService;

    @Resource
    private DriverRepository driverRepository;

    @Resource
    private VehicleRepository vehicleRepository;

    @Resource
    private SaleRepository saleRepository;

    @PostMapping("")
    public Distribution save(@RequestBody Distribution distribution) throws Exception {
        return distributionService.save(distribution);
    }

    @GetMapping("")
    public List<Distribution> findAll() {
        return distributionService.findAll();
    }

    @GetMapping("can")
    public Map<String, Object> can() {
        Map<String, Object> map = new HashMap<>();
        map.put("drivers", driverRepository.findAll());
        map.put("vehicles", vehicleRepository.findAll());
        map.put("sales",saleRepository.findAll()
                .stream()
                .filter(v-> !v.isPay())
                .collect(Collectors.toList()));
        return map;
    }

}
