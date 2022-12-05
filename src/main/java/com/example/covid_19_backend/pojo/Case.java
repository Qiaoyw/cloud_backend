package com.example.covid_19_backend.pojo;

import lombok.Data;

@Data
public class Case {
    private String sex;
    private Integer age;
    private String nationality;
    private String symptom;
    private String residence;
}
