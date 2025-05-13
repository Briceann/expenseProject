package edu.matc.util;

import edu.matc.rates.ExchangeRates;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    /**
     * Dynamically retrieves the rate for a given currency code using reflection.
     *
     * @param rates         the ExchangeRates object
     * @param currencyCode  e.g. "EUR", "JPY", "GBP"
     * @return the conversion rate
     */
    public static double getRateFor(ExchangeRates rates, String currencyCode) {
        try {
            String fieldName = currencyCode.toUpperCase();

            // Dynamically map field: USD → uSD, EUR → eUR
            String pojoField = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);

            Field field = rates.getClass().getDeclaredField(pojoField);
            field.setAccessible(true);
            Object value = field.get(rates);

            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                throw new IllegalArgumentException("Unsupported type for currency: " + currencyCode);
            }

        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Currency code not found: " + currencyCode, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access currency field: " + currencyCode, e);
        }
    }

    /**
     * Converts an amount from base currency to the target currency.
     *
     * @param amount        the original amount
     * @param rates         the ExchangeRates object
     * @param targetCurrency the target currency code
     * @return the converted amount
     */
    public static double convert(double amount, ExchangeRates rates, String targetCurrency) {
        double rate = getRateFor(rates, targetCurrency);
        double result = amount * rate;

        // Round to 2 decimal places
        return BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
