package alpha.ntr.gifget.model.service.gif.giphy;

import alpha.ntr.gifget.exceptions.GettingRatesException;
import alpha.ntr.gifget.exceptions.NoSuchCurrency;
import alpha.ntr.gifget.model.dto.GifDto;
import alpha.ntr.gifget.model.service.gif.GifService;
import alpha.ntr.gifget.model.service.exchangerates.openexchangerates.OpenExchangeRatesClient;
import alpha.ntr.gifget.model.service.exchangerates.ExchangeRatesService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:giphy.properties")
@Log
public class GiphyService implements GifService {

    private final ExchangeRatesService exchangeRatesService;

    private final GifMapper gifMapper;

    private GiphyClient client;

    @Value("${giphy.app.name}")
    private String appName;

    @Value("${giphy.api.key}")
    private String apiKey;

    @Value("${giphy.url}")
    private String url;

    @Value("${giphy.language}")
    private String language;

    @Value("${giphy.rating}")
    private String rating;

    @Value("${giphy.max.offset}")
    private int maxOffset;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @PostConstruct
    private void init() {
        client = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(OpenExchangeRatesClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GiphyClient.class, url);
    }


    @Override
    public GifDto getGif(String currencyCode) throws GettingRatesException, NoSuchCurrency {
        String query = exchangeRatesService.exchangeRateRise(currencyCode) ? "rich" : "broke";
        return gifMapper.toGifDto(getRandomGifByName(query));
    }

    private GifObject getRandomGifByName(String query) {
        return client.searchGif(apiKey, query, 1, getOffset(), rating, language).getData()[0];
    }

    private int getOffset() {
        return (new Random()).nextInt(maxOffset);
    }

}
