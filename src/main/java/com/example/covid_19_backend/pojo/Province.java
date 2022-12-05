package com.example.covid_19_backend.pojo;

import lombok.Data;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
public class Province implements Serializable {
    private String Province_State;
    private String Country_Region;
    private String Last_Update;
    private String Confirmed;
    private String Deaths;
    private String Recovered;
    private String Active;
}
