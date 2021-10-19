package alpha.ntr.gifget.model.service.exchangerates.openexchangerates;

import alpha.ntr.gifget.exceptions.GettingRatesException;
import alpha.ntr.gifget.exceptions.NoSuchCurrency;
import alpha.ntr.gifget.model.service.exchangerates.ExchangeRatesService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static alpha.ntr.gifget.model.service.exchangerates.openexchangerates.Currencies.*;


@Service
@PropertySource("classpath:open_exchange_rates.properties")
@Log
public class OpenExchangeRatesService implements ExchangeRatesService {

    private OpenExchangeRatesClient client;

    @Value("${openexchangerates.id}")
    private String appId;

    @Value("${openexchangerates.base}")
    private String base;

    @Value("${openexchangerates.api.url}")
    private String url;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private  Currencies BASE_CURRENCY = RUB;

    @PostConstruct
    private void init() {

        client = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(OpenExchangeRatesClient.class))
                .logLevel(Logger.Level.FULL)
                .target(OpenExchangeRatesClient.class, url);

        BASE_CURRENCY = Currencies.valueOf(base);

    }

    public OpenExchangeRatesObject getLatestRates() {
        OpenExchangeRatesObject rates = client.getLatestRates(appId);
        log.info(rates.toString());
        return rates;
    }

    public OpenExchangeRatesObject getRatesByDate(String date) {
        OpenExchangeRatesObject rates = client.getHistoryRates(appId, date);
        log.info(rates.toString());
        return rates;
    }

    public OpenExchangeRatesObject getTodayRates() {
        var today = LocalDate.now();
        return getRatesByDate(df.format(today));
    }

    public OpenExchangeRatesObject getYesterdayRates() {
        var yesterday = LocalDate.now().minusDays(1);
        return getRatesByDate(df.format(yesterday));
    }

    @Override
    public boolean exchangeRateRise(String currency) throws NoSuchCurrency, GettingRatesException {
       try {
           Currencies cur = Currencies.valueOf(currency.toUpperCase());
           return exchangeRateRise(cur, BASE_CURRENCY);
       } catch (IllegalArgumentException e) {
           throw new NoSuchCurrency(currency);
       }
    }

    public boolean exchangeRateRise(Currencies cur, Currencies baseCur) throws GettingRatesException {

        try {
            OpenExchangeRatesObject todayRates = getTodayRates();
            OpenExchangeRatesObject yesterdayRates = getYesterdayRates();

            Double todayRate = todayRates.getRates().get(cur.toString()) / todayRates.getRates().get(baseCur.toString());
            Double yesterdayRate= yesterdayRates.getRates().get(cur.toString()) /yesterdayRates.getRates().get(baseCur.toString());

            return  todayRate > yesterdayRate;
        } catch (Exception e) {
            throw new GettingRatesException();
        }
    }

}
