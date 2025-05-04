package edu.matc.persistence;

import edu.matc.rates.ExchangeRates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExchangeRateDaoTest {
    @Test
    public void testGetExchangeRates() throws Exception {
        ExchangeRatesDao dao = new ExchangeRatesDao();
        ExchangeRates rates = dao.getExchangeRates("USD");
        assertNotNull(rates);
    }
}
