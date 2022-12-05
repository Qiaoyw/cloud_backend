package com.example.covid_19_backend.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Total implements Serializable {
    private Long Confirmed;
    private Long Deceased;
    private Long Vaccinated;

    public Total(Long confirmed, Long deceased, Long vaccinated) {
        Confirmed = confirmed;
        Deceased = deceased;
        Vaccinated = vaccinated;
    }
}
