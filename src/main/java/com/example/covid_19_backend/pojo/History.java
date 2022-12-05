package com.example.covid_19_backend.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class History implements Serializable {
    private String continent;
    private String location;
    private String date;
    private String total_cases;
    private String new_cases;
    private String new_cases_smoothed;
    private String total_deaths;
    private String new_deaths;
    private String new_deaths_smoothed;
    private String total_cases_per_million;
    private String new_cases_per_million;
    private String new_cases_smoothed_per_million;
    private String total_deaths_per_million;
    private String new_deaths_per_million;
    private String new_deaths_smoothed_per_million;
    private String total_vaccinations;
    private String new_vaccinations;
    private String new_vaccinations_smoothed;
    private String total_vaccinations_per_hundred;
    private String new_vaccinations_smoothed_per_million;
    private String population;
}
