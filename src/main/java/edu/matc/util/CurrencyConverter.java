package edu.matc.util;

import edu.matc.rates.ExchangeRates;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for converting currency amounts based on dynamic exchange rates.
 * It uses reflection to retrieve rate values from the ExchangeRates POJO,
 * which has fields named after each currency code (e.g., usd, eur).
 */
public class CurrencyConverter {
    /**
     * Dynamically retrieves the rate for a given currency code from the ExchangeRates object
     *
     * @param rates         the ExchangeRates object
     * @param currencyCode  e.g. "EUR", "JPY", "GBP"
     * @return the conversion rate
     */
    public static double getRateFor(ExchangeRates rates, String currencyCode) {
        try {
            String fieldName = currencyCode.toUpperCase();

            // Normalize to match field name
            String pojoField = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);

            // Use reflection to access the matching field
            Field field = rates.getClass().getDeclaredField(pojoField);
            field.setAccessible(true);

            // Get the field value from the ExchangeRates object
            Object value = field.get(rates);

            // Convert to double, supporting both numeric and string values
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
     * Converts an amount from base currency to the specified target currency.
     *
     * @param amount        the original amount
     * @param rates         the ExchangeRates object
     * @param targetCurrency the target currency code
     * @return the converted amount
     */
    public static double convert(double amount, ExchangeRates rates, String targetCurrency) {
        double rate = getRateFor(rates, targetCurrency); // get exchange rates
        double result = amount * rate; // convert the amount

        // Round to 2 decimal places
        return BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
