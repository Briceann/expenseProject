package edu.matc.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.rates.ExchangeRates;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRatesDao {

    public ExchangeRates getExchangeRates(String baseCurrency) throws Exception {
        String apiUrl = "https://open.er-api.com/v6/latest/" + baseCurrency;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode ratesNode = root.get("rates");

        return mapper.treeToValue(ratesNode, ExchangeRates.class);
    }
}
