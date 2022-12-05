package com.example.covid_19_backend.pojo;

import lombok.Data;

@Data
public class Continent extends Country{
    public Continent(String name, String confirmed, String deceased, String tests, String active, String recovered, String vaccinated) {
        super(name, confirmed, deceased, tests, active, recovered, vaccinated);
    }
}
