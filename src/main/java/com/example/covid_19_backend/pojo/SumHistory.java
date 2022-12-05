package com.example.covid_19_backend.pojo;

import lombok.Data;

@Data
public class SumHistory {
    private Long confirmed;
    private Long death;
    private Long recovered;
    private Long vaccinations;
    private String Last_Update;

    public SumHistory() {
        confirmed = 0L;
        death = 0L;
        recovered = 0L;
        vaccinations = 0L;
    }

    public void addChina(String confirm, String death, String recovered) {
        if (confirm != null && !confirm.equals("")) {
            double temp = Double.parseDouble(confirm);
            long sum = (long) temp + getConfirmed();
            setConfirmed(sum);
        }
        if (death != null && !death.equals("")) {
            double temp = Double.parseDouble(death);
            long sum = (long) temp + getDeath();
            setDeath(sum);
        }
        if (recovered != null && !recovered.equals("")) {
            double temp = Double.parseDouble(recovered);
            long sum = (long) temp + getRecovered();
            setRecovered(sum);
        }
    }

    public void addWorld(String confirm, String death, String vaccinations) {
        if (confirm != null && !(confirm.trim().length() == 0)) {
            double temp = Double.parseDouble(confirm);
            long sum = (long) temp + getConfirmed();
            setConfirmed(sum);
        }
        if (death != null && !(death.trim().length() == 0)) {
            double temp = Double.parseDouble(death);
            long sum = (long) temp + getDeath();
            setDeath(sum);
        }
        if (vaccinations != null && !(vaccinations.trim().length() == 0)) {
            double temp = Double.parseDouble(vaccinations);
            long sum = (long) temp + getVaccinations();
            setVaccinations(sum);
        }
    }
}
