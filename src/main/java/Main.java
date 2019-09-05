import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final String URL = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";

    public static void main(String[] args) {
        getUsd();
    }

    private static String getDate() {
        System.out.println("Enter your date in format dd.mm.yyyy: ");
        Scanner scanner = new Scanner(System.in);
        String dataFromConsole = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("dd.mm.yyyy").parse(dataFromConsole);
            return dataFromConsole;
        } catch (ParseException e) {
            System.out.println("Error! Invalid date format! ");
            return null;
        }
    }

    private static String addDateInURL(String date) {
        StringBuilder sb = new StringBuilder(URL);
        sb.append(date);
        return sb.toString();
    }

    private static String getUsd() {
        String date = addDateInURL(getDate());
        String json = HttpUtil.sendRequest(date, null, null);
        Gson gson = new Gson();
        PrivatResponse response = gson.fromJson(json, PrivatResponse.class);
        for (ExchangeRate rate : response.getExchangeRate()) {
            if ("USD".equals(rate.getCurrency())) {
                System.out.println("Rate: " + rate.getSaleRateNB());
                break;
            }
        }
        return null;
    }
}
