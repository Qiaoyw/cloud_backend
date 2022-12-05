package com.example.covid_19_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brief {
    private String location;
    private Object confirm;
    private String date;
}
