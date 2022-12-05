package com.example.covid_19_backend.util;

import com.example.covid_19_backend.pojo.*;

public class FormatUtil {
    public static void RemoveComma(Country country) {
        String confirmed = country.getConfirmed().replace(",", "");
        country.setConfirmed(confirmed);
        String deceased = country.getDeceased().replace(",", "");
        country.setDeceased(deceased);
        String test = country.getTests().replace(",", "");
        country.setTests(test);
        String active = country.getActive().replace(",", "");
        country.setActive(active);
        String recovered = country.getRecovered().replace(",", "");
        country.setRecovered(recovered);
        String vaccinated = country.getVaccinated().replace(",", "");
        country.setVaccinated(vaccinated);
        if (country instanceof Africa) {
            Africa africa = (Africa) country;
            String population = africa.getPopulation().replace(",", "");
            africa.setPopulation(population);
        }else if (country instanceof Asia) {
            Asia asia = (Asia) country;
            String population = asia.getPopulation().replace(",", "");
            asia.setPopulation(population);
        }else if (country instanceof Australia) {
            Australia australia = (Australia) country;
            String population = australia.getPopulation().replace(",", "");
            australia.setPopulation(population);
        }else if (country instanceof Canada) {
            Canada canada = (Canada) country;
            String population = canada.getPopulation().replace(",", "");
            canada.setPopulation(population);
        }else if (country instanceof Europe) {
            Europe europe = (Europe) country;
            String population = europe.getPopulation().replace(",", "");
            europe.setPopulation(population);
        }else if (country instanceof Italy) {
            Italy italy = (Italy) country;
            String population = italy.getPopulation().replace(",", "");
            italy.setPopulation(population);
        }else if (country instanceof NorthAmerica) {
            NorthAmerica northAmerica = (NorthAmerica) country;
            String population = northAmerica.getPopulation().replace(",", "");
            northAmerica.setPopulation(population);
        }else if (country instanceof Oceania) {
            Oceania oceania = (Oceania) country;
            String population = oceania.getPopulation().replace(",", "");
            oceania.setPopulation(population);
        }else if (country instanceof Russia) {
            Russia russia = (Russia) country;
            String population = russia.getPopulation().replace(",", "");
            russia.setPopulation(population);
        }else if (country instanceof SouthAmerica) {
            SouthAmerica southAmerica = (SouthAmerica) country;
            String population = southAmerica.getPopulation().replace(",", "");
            southAmerica.setPopulation(population);
        }else if (country instanceof USA) {
            USA usa = (USA) country;
            String population = usa.getPopulation().replace(",", "");
            usa.setPopulation(population);
        }
    }
}
