package com.example.covid_19_backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.covid_19_backend.pojo.*;
import com.example.covid_19_backend.service.impl.DataServiceImpl;
import com.example.covid_19_backend.util.RedisUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "疫情数据信息")
@RequestMapping("data")
public class DataController {

    @Autowired
    private DataServiceImpl dataService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/all") // /data/all
    public ResponseData getAllCountry() {
        List<Country> countries = dataService.getAllCountryData();
        return ResponseData.Success("获取成功", countries, 200);
    }

    @GetMapping("/africa")
    public ResponseData getAfrica() {
        List<Africa> africas = dataService.getAfricaData();
        return ResponseData.Success("获取成功", africas, 200);
    }

    @GetMapping("/asia")
    public ResponseData getAsia() {
        List<Asia> asias = dataService.getAsiaData();
        return ResponseData.Success("获取成功", asias, 200);
    }

    @GetMapping("/australia")
    public ResponseData getAustralia() {
        List<Australia> australias = dataService.getAustraliaData();
        return ResponseData.Success("获取成功", australias, 200);
    }

    @GetMapping("/canada")
    public ResponseData getCanada() {
        List<Canada> canadas = dataService.getCanadaData();
        return ResponseData.Success("获取成功", canadas, 200);
    }

    @GetMapping("/europe")
    public ResponseData getEurope() {
        List<Europe> europes = dataService.getEuropeData();
        return ResponseData.Success("获取成功", europes, 200);
    }

    @GetMapping("/italy")
    public ResponseData getItaly() {
        List<Italy> italies = dataService.getItalyData();
        return ResponseData.Success("获取成功", italies, 200);
    }

    @GetMapping("/northAmerica")
    public ResponseData getNorthAmerica() {
        List<NorthAmerica> northAmericas = dataService.getNorthAmericaData();
        return ResponseData.Success("获取成功", northAmericas, 200);
    }

    @GetMapping("/oceania")
    public ResponseData getOceania() {
        List<Oceania> oceanias = dataService.getOceaniaData();
        return ResponseData.Success("获取成功", oceanias, 200);
    }

    @GetMapping("/russia")
    public ResponseData getRussia() {
        List<Russia> russias = dataService.getRussiaData();
        return ResponseData.Success("获取成功", russias, 200);
    }

    @GetMapping("/southAmerica")
    public ResponseData getSouthAmerica() {
        List<SouthAmerica> southAmericas = dataService.getSouthAmericaData();
        return ResponseData.Success("获取成功", southAmericas, 200);
    }

    @GetMapping("/usa")
    public ResponseData getUSA() {
        List<USA> usas = dataService.getUSAData();
        return ResponseData.Success("获取成功", usas, 200);
    }

    @GetMapping("/total")
    public ResponseData getTotalNumber() {
        Total total = dataService.getTotalNumber();
        return ResponseData.Success("success", total, 200);
    }

    @GetMapping("/province")
    public ResponseData getProvince() {
        Object provinces = dataService.getAllProvince();
        return ResponseData.Success("success", provinces, 200);
    }

    @GetMapping("/risk")
    public ResponseData getRiskArea() {
        Object riskAreas = dataService.getRiskArea();
        JSONObject object = (JSONObject) riskAreas;
        return ResponseData.Success("success", object.get("statisGradeCityDetail"), 200);
    }

    @GetMapping("/history")
    public ResponseData getHistory(@RequestParam(name = "country") String country) {
        List<History> histories = dataService.getHistoryDataByCountryName(country);
        histories.sort(Comparator.comparing(History::getDate));
        return ResponseData.Success("success", histories, 200);
    }

    @GetMapping("/latestPolicy")
    public ResponseData getLatestPolicy() {
        List<LatestPolicy> latestPolicies = dataService.getAllLatestPolicy();
        return ResponseData.Success("获取成功", latestPolicies, 200);
    }

    @PostMapping("/getLatestPolicyById")
    public ResponseData getLatestPolicyById(@RequestBody LatestPolicyEntity latestPolicyEntity){
        try{
            LatestPolicy latestPolicy = dataService.getLatestPolicyById(latestPolicyEntity.id);
            return ResponseData.Success("获取成功", latestPolicy, 200);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @GetMapping("/provincePolicy")
    public ResponseData getProvincePolicys() {
        try {
            return dataService.getAllProvincePolicies();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @PostMapping("/getProvincePolicyById")
    public ResponseData getProvincePolicyById(@RequestBody LatestPolicyEntity provincePolicyEntity) {
        try {
            ProvincePolicy provincePolicyById = dataService.getProvincePolicyById(provincePolicyEntity.id);
            return ResponseData.Success("获取成功", provincePolicyById, 200);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

   @GetMapping("/continent")
    public ResponseData getContinent() {
        List<Continent> continents = dataService.getContinentSumInfo();
        return ResponseData.Success("success", continents, 200);
    }

    @GetMapping("/top15")
    public ResponseData getTop15() {
        List<Country> countries = dataService.getTop15();
        return ResponseData.Success("success", countries, 200);
    }

    @GetMapping("/riskArea")
    public ResponseData getAllRiskAreas() {
        try {
            return dataService.getAllRiskAreas();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }


    @GetMapping("/h-province")
    public ResponseData getProvinceHistory(@RequestParam(name = "province") String province) {
        List<Province> histories = dataService.getProvinceHistory(province);
        return ResponseData.Success("success", histories, 200);
    }

    @GetMapping("/flightToAbroad")
    public ResponseData getFlightToAbroad() {
        try {
            return dataService.getFlightToAbroad();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @GetMapping("/flightToChina")
    public ResponseData getFlightToChina() {
        try {
            return dataService.getFlightToChina();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @GetMapping("/beginningFlightsChina")
    public ResponseData getBeginningFlightsChina(){
        try{
            return dataService.getBeginningFlightsChina();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @GetMapping("/arrivalFlightsChina")
    public ResponseData getArrivalFlightsChina(){
        try{
            return dataService.getArrivalFlightsChina();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }


    @GetMapping("/railway")
    public ResponseData getRailway() {
        try {
            return dataService.getRailway();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }
    
    @GetMapping("/vaccine")
    public ResponseData getVaccine() {
        try {
            return dataService.getVaccine();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @GetMapping("/trajectory")
    public ResponseData getTrajectory() {
        List<Trajectory> trajectoryList = dataService.getTrajectory();
        return ResponseData.Success("success", trajectoryList, 200);
    }

    @GetMapping("/h-China")
    public ResponseData getChinaHistory() {
        List<SumHistory> china = dataService.getChinaHistory();
        return ResponseData.Success("success", china, 200);
    }

    @GetMapping("/h-World")
    public ResponseData getWorldHistory() {
        Object world = redisUtil.get("history_world");
        return ResponseData.Success("success", world, 200);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class LatestPolicyEntity{
        private Integer id;
    }
}
