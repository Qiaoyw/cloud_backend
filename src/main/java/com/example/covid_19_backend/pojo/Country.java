package com.example.covid_19_backend.pojo;

import lombok.Data;

@Data
public class Country {
    private String name;
    private String Confirmed;
    private String Deceased;
    private String Tests;
    private String Active;
    private String Recovered;
    private String Vaccinated;

    public Country(String name, String confirmed, String deceased, String tests, String active, String recovered, String vaccinated) {
        this.name = name;
        Confirmed = confirmed;
        Deceased = deceased;
        Tests = tests;
        Active = active;
        Recovered = recovered;
        Vaccinated = vaccinated;
    }

    public Country(){}
}
