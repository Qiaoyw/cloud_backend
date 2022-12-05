package com.example.covid_19_backend.service;

import com.example.covid_19_backend.pojo.*;

import java.util.List;


public interface DataService{

    List<Country> getAllCountryData();

    List<LatestPolicy> getAllLatestPolicy();
    LatestPolicy getLatestPolicyById(Integer id);

    ResponseData getAllProvincePolicies();
    ProvincePolicy getProvincePolicyById(Integer id);

    ResponseData getAllRiskAreas();
    List<String> getAllRiskAreasCity();

    ResponseData getFlightToAbroad();
    ResponseData getFlightToChina();

    ResponseData getBeginningFlightsChina();
    ResponseData getArrivalFlightsChina();

    ResponseData getRailway();
    ResponseData getVaccine();
}
