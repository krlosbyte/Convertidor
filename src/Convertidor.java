import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Convertidor {
    private static final String API_KEY = "7e606174f4315d884027fdee";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println(">>Conversor de Monedas<<\n");
            System.out.println("1) USD a MXN");
            System.out.println("2) MXN a USD");
            System.out.println("3) USD a ARS");
            System.out.println("4) ARS a USD");
            System.out.println("5) USD a BRL");
            System.out.println("6) BRL a USD");
            System.out.println("7) Salir");
            System.out.print("\nElija una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> convertir("USD", "MXN");
                case 2 -> convertir("MXN", "USD");
                case 3 -> convertir("USD", "ARS");
                case 4 -> convertir("ARS", "USD");
                case 5 -> convertir("USD", "BRL");
                case 6 -> convertir("BRL", "USD");
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        } while (opcion != 7);

        scanner.close();
    }

    private static void convertir(String from, String to) {
        try {
            String urlStr = BASE_URL + API_KEY + "/latest/" + from;
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            InputStreamReader reader = new InputStreamReader((request.getInputStream()));
            ExchRate response = new Gson().fromJson(reader, ExchRate.class);

            if (response != null && response.conversion_rates.containsKey(to)) {
                double rate = response.conversion_rates.get(to);
                Scanner sc = new Scanner(System.in);
                System.out.print("Ingrese cantidad en " + from + ": ");
                double amount = sc.nextDouble();
                double converted = amount * rate;
                System.out.printf("%.2f %s = %.2f %s%n", amount, from, converted, to);
            } else {
                System.out.println("Error al obtener tipo de cambio.");
            }
        } catch (Exception e) {
            System.out.println("Error en la conversión: " + e.getMessage());
        }
    }
}
