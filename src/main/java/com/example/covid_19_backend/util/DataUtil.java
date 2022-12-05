package com.example.covid_19_backend.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataUtil {
    private static final String dir = "./data";

    private static void getData(String name, String URl) {
        try {
            URL url = new URL(URl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println("get inputStream");
            InputStream input = connection.getInputStream();
            System.out.println("end inputStream");
            String fileName = dir + '/' + name + ".txt";
            File file =  new File(fileName);
            if (file.exists()) file.delete();
            file.createNewFile();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            String ch = reader.readLine();
            //ch.replaceAll("Province/State", "Province_State");
            //ch.replaceAll("Country/Region", "Country_Region");
            writer.write(ch);
            writer.newLine();
            while((ch = reader.readLine()) != null){
                writer.write(ch);
                writer.newLine();
            }
            writer.close();
            input.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getChina(String URl, String name) {
        String result = null;
        try {
            URL url = new URL(URl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = reader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            result = responseStrBuilder.toString();
            input.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void BatchImport() {
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c confirmed_global  --jsonArray --file ./data/confirmed_global.json");
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c confirmed_us  --jsonArray --file ./data/confirmed_us.json");
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c daily_report  --jsonArray --file ./data/daily_report.json");
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c deaths_global  --jsonArray --file ./data/deaths_global.json");
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c deaths_us  --jsonArray --file ./data/confirmed_us.json");
            run.exec("mongoimport -h 10.251.254.107 --port 27017 -u covid19user -p c0v1d -d covid19 -c recovered_global  --jsonArray --file ./data/recovered_global.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertUrl(String month, String day, String year) {
        String base = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
        if (month.length() == 1) month = "0" + month;
        if (day.length() == 1) day = "0" + day;
        String url = base + month + "-" + day + "-" + year + ".csv";
        String name = month + "-" + day + "-" + year;
        System.out.println(name);
        getData(name, url);
    }

    public static void main(String[] args) {
//        File file = new File(dir);
//        if (!file.exists()) file.mkdirs();
//
//        String confirmed_us_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
//        String confirmed_us_name = "confirmed_us";
//        getData(confirmed_us_name, confirmed_us_url);
//
//        String confirmed_global_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
//        String confirmed_global_name = "confirmed_global";
//        getData(confirmed_global_name, confirmed_global_url);
//
//        String deaths_us_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_US.csv";
//        String deaths_us_name = "deaths_us";
//        getData(deaths_us_name, deaths_us_url);
//
//        String deaths_global_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
//        String deaths_global_name = "deaths_global";
//        getData(deaths_global_name, deaths_global_url);
//
//        String recovered_global_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
//        String recovered_global_name = "recovered_global";
//        getData(recovered_global_name, recovered_global_url);
//
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        int day = Integer.parseInt(date.format(formatter).toString().split("-")[0]);
//        String add = "";
//        if (day - 1 < 10) add = "0" + (day - 1);
//        else add += day;
//        String daily_report = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/07-"+ add + "-2021.csv";
//        String daily_report_name = "daily_report";
//        getData(daily_report_name, daily_report);

//        BatchImport();
//        int[] day_2020 = new int[]{0, 32, 30, 32, 31, 32, 31, 32, 32, 31, 32, 31, 32};
//        int[] day_2021 = new int[]{0, 32, 29, 32, 31, 32, 31, 10};
//        for (int i = 22; i <= 31; ++i) {
//            convertUrl("1", String.valueOf(i), "2020");
//        }
//        for (int i = 2; i <= 12; ++i) {
//            for (int j = 1; j < day_2020[i]; ++j) {
//                convertUrl(String.valueOf(i), String.valueOf(j), "2020");
//            }
//        }
//        for (int i = 1; i <= 7; ++i) {
//            for (int j = 1; j < day_2021[i]; ++j) {
//                convertUrl(String.valueOf(i), String.valueOf(j), "2021");
//            }
//        }
    }
}
