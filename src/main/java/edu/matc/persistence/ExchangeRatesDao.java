package edu.matc.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.rates.ExchangeRates;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * DAO class for retrieving currency exchange rate data from the ExchangeRate API.
 * Uses Java's built-in HttpClient to send a GET request to the API and parse the response
 * into an ExchangeRates POJO using Jackson.
 */
public class ExchangeRatesDao {

    /**
     * Retrieves exchange rates for the specified base currency.
     *
     * @param baseCurrency the base currency code (e.g., "USD", "EUR")
     * @return an ExchangeRates object containing mapped rate values
     * @throws Exception if the HTTP request fails or JSON parsing fails
     */
    public ExchangeRates getExchangeRates(String baseCurrency) throws Exception {
        String apiUrl = "https://open.er-api.com/v6/latest/" + baseCurrency;

        // Create a reusable HTTP client instance
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // Build the GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode ratesNode = root.get("rates");

        // Convert the "rates" node into an ExchangeRates object
        return mapper.treeToValue(ratesNode, ExchangeRates.class);
    }
}
