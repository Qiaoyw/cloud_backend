package com.example.covid_19_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.example.covid_19_backend.mapper.*;

import com.example.covid_19_backend.pojo.*;
import com.example.covid_19_backend.pojo.common.Constant;
import com.example.covid_19_backend.service.DataService;
import com.example.covid_19_backend.util.DataUtil;
import com.example.covid_19_backend.util.FormatUtil;


import com.example.covid_19_backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProvincePolicyMapper provincePolicyMapper;

    @Autowired
    private LatestPolicyMapper latestPolicyMapper;

    @Autowired
    private RiskAreaMapper riskAreaMapper;

    @Autowired
    private FlightToAbroadMapper flightToAbroadMapper;

    @Autowired
    private FlightToChinaMapper flightToChinaMapper;

    @Autowired
    private BeginningFlightsChinaMapper beginningFlightsChinaMapper;

    @Autowired
    private ArrivalFlightsChinaMapper arrivalFlightsChinaMapper;

    @Autowired
    private RailwayMapper railwayMapper;

    public Total getTotalNumber() {
        List<Country> countries = mongoTemplate.findAll(Country.class, "World");
        Long[] totals = new Long[3];
        totals[0] = 0L;
        totals[1] = 0L;
        totals[2] = 0L;
        for (Country country : countries) {
            String confirmed = country.getConfirmed().replace(",", "");
            if (confirmed.matches("[0-9]+")) totals[0] += Long.parseLong(confirmed);
            String deceased = country.getDeceased().replace(",", "");
            if (deceased.matches("[0-9]+")) totals[1] += Long.parseLong(deceased);
            String vaccinated = country.getVaccinated().replace(",", "");
            if (vaccinated.matches("[0-9]+")) totals[2] += Long.parseLong(vaccinated);
        }
        return new Total(totals[0], totals[1], totals[2]);
    }

    @Override
    public ResponseData getAllProvincePolicies() {
        try {
//            QueryWrapper<ProvincePolicy> queryWrapper = new QueryWrapper<>();
            List<ProvincePolicy> policyList = provincePolicyMapper.getAllProvincePolicies();
            return ResponseData.Success("获取各省市防疫政策成功", policyList, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ProvincePolicy getProvincePolicyById(Integer id) {
        try {
            return provincePolicyMapper.getProvincePolicyById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Country> getAllCountryData() {
        List<Country> countries = mongoTemplate.findAll(Country.class, "World");
        for (Country country : countries) {
            FormatUtil.RemoveComma(country);
        }
        countries.sort((country, t1) -> {
            if (country.getConfirmed().equals("Unknown") && !t1.getConfirmed().equals("Unknown")) return 1;
            else if (!country.getConfirmed().equals("Unknown") && t1.getConfirmed().equals("Unknown")) return -1;
            else if (country.getConfirmed().equals("Unknown") && t1.getConfirmed().equals("Unknown")) return 0;
            else {
                long a1 = Long.parseLong(country.getConfirmed());
                long a2 = Long.parseLong(t1.getConfirmed());
                if (a1 < a2) return 1;
                else if (a1 == a2) return 0;
                else return -1;
            }
        });
        return countries;
    }

    @Override
    public ResponseData getAllRiskAreas(){
        try {
            List<RiskArea> RiskAreaList = riskAreaMapper.getAllRiskAreas();
            return ResponseData.Success("获取风险地区成功", RiskAreaList, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ResponseData getFlightToAbroad(){
        try {
            List<FlightToAbroad> flightToAbroad = flightToAbroadMapper.getFlightToAbroad();
            return ResponseData.Success("获取飞往高风险国家航班成功", flightToAbroad, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ResponseData getFlightToChina(){
        try {
            List<FlightToChina> flightToChina = flightToChinaMapper.getFlightToChina();
            return ResponseData.Success("获取高风险国家飞往国内航班成功", flightToChina, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ResponseData getBeginningFlightsChina(){
        try {
            List<BeginningFlightsChina> beginningFlightsChina = beginningFlightsChinaMapper.getBeginningFlightsChina();
            return ResponseData.Success("获取国内中高风险地区起飞航班成功成功", beginningFlightsChina, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ResponseData getArrivalFlightsChina(){
        try {
            List<ArrivalFlightsChina> arrivalFlightsChina = arrivalFlightsChinaMapper.getArrivalFlightsChina();
            return ResponseData.Success("获取国内飞往中高风险地区航班成功", arrivalFlightsChina, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    public ResponseData getRailway(){
        try {
            List<Railway> railways = railwayMapper.getRailway();
            return ResponseData.Success("获取国内中高风险地区铁路线路成功", railways, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }


    @Override
    public List<LatestPolicy> getAllLatestPolicy() {
        return latestPolicyMapper.getAllLatestPolicy();
    }

    @Override
    public LatestPolicy getLatestPolicyById(Integer id) {
        return latestPolicyMapper.getLatestPolicyById(id);
    }

    public List<Africa> getAfricaData() {
        List<Africa> africaList = mongoTemplate.findAll(Africa.class, "Africa");
        for (Africa africa : africaList) {
            FormatUtil.RemoveComma(africa);
        }
        return africaList;
    }

    public List<Asia> getAsiaData() {
        List<Asia> asiaList = mongoTemplate.findAll(Asia.class, "Asia");
        for (Asia asia : asiaList) {
            FormatUtil.RemoveComma(asia);
        }
        return asiaList;
    }

    public List<Australia> getAustraliaData() {
        List<Australia> australiaList = mongoTemplate.findAll(Australia.class, "Australia");
        for (Australia australia : australiaList) {
            FormatUtil.RemoveComma(australia);
        }
        return australiaList;
    }

    public List<Canada> getCanadaData() {
        List<Canada> canadaList =  mongoTemplate.findAll(Canada.class, "Canada");
        for (Canada canada : canadaList) {
            FormatUtil.RemoveComma(canada);
        }
        return canadaList;
    }

    public List<Europe> getEuropeData() {
        return mongoTemplate.findAll(Europe.class, "Europe");
    }

    public List<Italy> getItalyData() {
        List<Italy> italyList = mongoTemplate.findAll(Italy.class, "Italy");
        for (Italy italy : italyList) {
            FormatUtil.RemoveComma(italy);
        }
        return italyList;
    }

    public List<NorthAmerica> getNorthAmericaData() {
        List<NorthAmerica> northAmericaList = mongoTemplate.findAll(NorthAmerica.class, "NorthAmerica");
        for (NorthAmerica northAmerica : northAmericaList) {
            FormatUtil.RemoveComma(northAmerica);
        }
        return northAmericaList;
    }

    public List<Oceania> getOceaniaData() {
        List<Oceania> oceaniaList = mongoTemplate.findAll(Oceania.class, "Oceania");
        for (Oceania oceania : oceaniaList) {
            FormatUtil.RemoveComma(oceania);
        }
        return oceaniaList;
    }

    public List<Russia> getRussiaData() {
        List<Russia> russiaList = mongoTemplate.findAll(Russia.class, "Russia");
        for (Russia russia : russiaList) {
            FormatUtil.RemoveComma(russia);
        }
        return russiaList;
    }

    public List<SouthAmerica> getSouthAmericaData() {
        List<SouthAmerica> southAmericaList = mongoTemplate.findAll(SouthAmerica.class, "SouthAmerica");
        for (SouthAmerica southAmerica : southAmericaList) {
            FormatUtil.RemoveComma(southAmerica);
        }
        return southAmericaList;
    }

    public List<USA> getUSAData() {
        List<USA> usaList = mongoTemplate.findAll(USA.class, "USA");
        for (USA usa : usaList) {
            FormatUtil.RemoveComma(usa);
        }
        return usaList;
    }

    public Object getAllProvince() {
        return redisUtil.get("China");
    }

    public Object getRiskArea() {
        String RISK_URL = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=statisGradeCityDetail";
        String risk = DataUtil.getChina(RISK_URL, "risk");
        JSONObject json = JSON.parseObject(risk);
        return json.get("data");
    }
    public List<String> getAllRiskAreasCity(){
        return riskAreaMapper.getAllRiskAreasCity();
    }

    public List<History> getHistoryDataByCountryName(String country) {
        Query query = new Query(Criteria.where("location").is(country));
        List<History> histories = mongoTemplate.find(query, History.class, "history_data");
        for (History history : histories) {
            if (history.getNew_cases().contains("-")) {
                history.setNew_cases("0");
                history.setNew_cases_smoothed("0");
                history.setNew_cases_per_million("0");
                history.setNew_cases_smoothed_per_million("0");
            }
            if (history.getNew_deaths().contains("-")) {
                history.setNew_deaths("0");
                history.setNew_deaths_smoothed("0");
                history.setNew_deaths_per_million("0");
                history.setNew_deaths_smoothed_per_million("0");
            }
        }
        return histories;
    }

    public List<Continent> getContinentSumInfo() {
        List<Continent> continents = new ArrayList<>();
        for (String continent : Constant.CONTINENT) {
            List<Country> countries = mongoTemplate.findAll(Country.class, continent);
            long[] total = new long[6];
            for (Country country : countries) {
                FormatUtil.RemoveComma(country);
                total[0] += changeToLong(country.getConfirmed());
                total[1] += changeToLong(country.getDeceased());
                total[2] += changeToLong(country.getTests());
                total[3] += changeToLong(country.getActive());
                total[4] += changeToLong(country.getRecovered());
                total[5] += changeToLong(country.getVaccinated());
            }
            Continent continent1 = new Continent(continent, String.valueOf(total[0]), String.valueOf(total[1]), String.valueOf(total[2]), String.valueOf(total[3]), String.valueOf(total[4]), String.valueOf(total[5]));
            continents.add(continent1);
        }
        return continents;
    }

    public List<Country> getTop15() {
        List<Country> countries = mongoTemplate.findAll(Country.class, "World");
        for (Country country : countries) {
            FormatUtil.RemoveComma(country);
        }
        countries.sort((country, t1) -> {
            if (country.getConfirmed().equals("Unknown") && !t1.getConfirmed().equals("Unknown")) return 1;
            else if (!country.getConfirmed().equals("Unknown") && t1.getConfirmed().equals("Unknown")) return -1;
            else if (country.getConfirmed().equals("Unknown") && t1.getConfirmed().equals("Unknown")) return 0;
            else {
                long a1 = Long.parseLong(country.getConfirmed());
                long a2 = Long.parseLong(t1.getConfirmed());
                if (a1 < a2) return 1;
                else if (a1 == a2) return 0;
                else return -1;
            }
        });

        return countries.subList(0, 15);
    }

    private long changeToLong(String num) {
        if (num.matches("[0-9]+")) {
            return Long.parseLong(num);
        }
        return 0L;
    }

    public List<Province> getProvinceHistory(String province) {
        Query query = new Query(Criteria.where("Province_State").is(province));
        List<Province> provinces = mongoTemplate.find(query, Province.class, "Everyday");
        provinces.sort(Comparator.comparing(Province::getLast_Update));
        return provinces;
    }

    public ResponseData getVaccine(){
        try {
            List<Vaccine> vaccines = mongoTemplate.findAll(Vaccine.class, "vaccine");
            return ResponseData.Success("获取各省疫苗数据成功", vaccines, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    public List<Trajectory> getTrajectory() {
        List<Trajectory> trajectoryList = mongoTemplate.findAll(Trajectory.class, "Trajectory");
        return trajectoryList;
    }

    public List<SumHistory> getChinaHistory() {
        List<SumHistory> china = mongoTemplate.findAll(SumHistory.class, "China");
        china.sort(Comparator.comparing(SumHistory::getLast_Update));
        return china;
    }

    public List<SumHistory> getWorldHistory() {
        List<History> countries = mongoTemplate.findAll(History.class, "history_data");
        countries.sort(Comparator.comparing(History::getDate));
        int len = countries.size();
        List<SumHistory> world = new ArrayList<>();
        SumHistory history = new SumHistory();
        history.setLast_Update(countries.get(0).getDate());
        for (int i = 0; i < len; ++i) {
            History temp = countries.get(i);
            if (!temp.getDate().equals(history.getLast_Update())) {
                world.add(history);
                history = new SumHistory();
                history.setLast_Update(temp.getDate());
            }
            history.addWorld(temp.getTotal_cases(), temp.getTotal_deaths(), temp.getTotal_vaccinations());
        }
        return world;
    }
}
