package alpha.ntr.gifget.model.service.exchangerates;

import alpha.ntr.gifget.exceptions.GettingRatesException;
import alpha.ntr.gifget.exceptions.NoSuchCurrency;

public interface ExchangeRatesService {
    boolean exchangeRateRise(String currency) throws IllegalArgumentException, NoSuchCurrency, GettingRatesException;
}
