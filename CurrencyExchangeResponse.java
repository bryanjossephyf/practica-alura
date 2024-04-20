import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

public class CurrencyConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }
}

public class CurrencyConverter {

    private final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/833f560fb618b013dfdf7d40/latest/";


    private RestTemplate restTemplate;

    public double convertCurrency(String from, String to, double amount) {
        String apiUrl = API_BASE_URL + from.toUpperCase();
        CurrencyExchangeResponse response = restTemplate.getForObject(apiUrl, CurrencyExchangeResponse.class);
        if (response != null && response.getConversionRates().containsKey(to.toUpperCase())) {
            double rate = response.getConversionRates().get(to.toUpperCase());
            return amount * rate;
        }
        return -1;
    }
}


public class CurrencyExchangeResponse {
    private Map<String, Double> conversion_rates;

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}

public class ConsoleMenu implements CommandLineRunner {


    private CurrencyConverter currencyConverter;


    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("============CONVERSOR DE MONEDAS============");
            System.out.println("Elija una opción:");
            System.out.println("1- Dólar => Peso Argentino");
            System.out.println("2- Peso Argentino => Dólar");
            System.out.println("3- Dólar => Real Brasileño");
            System.out.println("4- Real Brasileño => Dólar");
            System.out.println("5- Dólar => Peso Colombiano");
            System.out.println("6- Peso Colombiano => Dólar");
            System.out.println("7- Salir");

            int option = scanner.nextInt();
            double amount;
            double result;

            switch (option) {
                case 1:
                    System.out.println("Ingrese la cantidad en dólares:");
                    amount = scanner.nextDouble();
                    result = currencyConverter.convertCurrency("USD", "ARS", amount);
                    System.out.println("El equivalente en pesos argentinos es: " + result);
                    break;
                case 2:
                    System.out.println("Ingrese la cantidad en pesos argentinos:");
                    amount = scanner.nextDouble();
                    result = currencyConverter.convertCurrency("ARS", "USD", amount);
                    System.out.println("El equivalente en dólares es: " + result);
                    break;

                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        System.out.println("¡Gracias por usar el conversor de monedas!");
    }
}
