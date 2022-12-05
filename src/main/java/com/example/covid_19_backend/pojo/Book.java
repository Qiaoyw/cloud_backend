package com.example.covid_19_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private String province;
    private String location;
    private boolean isBook;
}
