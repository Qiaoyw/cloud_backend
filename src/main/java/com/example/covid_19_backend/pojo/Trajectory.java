package com.example.covid_19_backend.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Trajectory {
    private List<Case> cases;
    private String trajectory;
}
