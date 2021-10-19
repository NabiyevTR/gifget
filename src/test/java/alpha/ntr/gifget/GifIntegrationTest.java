package alpha.ntr.gifget;

import alpha.ntr.gifget.exceptions.NoSuchCurrency;
import alpha.ntr.gifget.model.service.exchangerates.openexchangerates.OpenExchangeRatesObject;
import alpha.ntr.gifget.model.service.exchangerates.openexchangerates.OpenExchangeRatesService;
import alpha.ntr.gifget.model.service.gif.giphy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GifIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GifMapper mapper;

    @MockBean
    private GiphyService giphyService;

    @MockBean
    private OpenExchangeRatesService exchangeRatesService;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @BeforeEach
    void setup() {

        Mockito.reset(exchangeRatesService, giphyService);

        GifObject[] gifObjectsRich = new GifObject[]{
                GifObject.builder()
                        .url("rich")
                        .bitly_url("rich")
                        .embed_url("rich")
                        .build()
        };

        GifObject[] gifObjectsBroke = new GifObject[]{
                GifObject.builder()
                        .url("broke")
                        .bitly_url("broke")
                        .embed_url("broke")
                        .build()
        };

        var todayDate = LocalDate.now();
        String yesterday = df.format(todayDate.minusDays(1));
        String today = df.format(todayDate);

        Map<String, Double> yesterdayRates = new HashMap<>();
        yesterdayRates.put("EUR", 0.8);
        yesterdayRates.put("USD", 1.0);
        yesterdayRates.put("RUB", 80.0);

        var yesterdayRatesObject = new OpenExchangeRatesObject();
        yesterdayRatesObject.setBase("USD");
        yesterdayRatesObject.setRates(yesterdayRates);

        Map<String, Double> todayRates = new HashMap<>();
        todayRates.put("EUR", 0.2);
        todayRates.put("USD", 1.0);
        todayRates.put("RUB", 100.0);

        var todayRatesObject = new OpenExchangeRatesObject();
        todayRatesObject.setBase("USD");
        todayRatesObject.setRates(todayRates);


        Mockito.when(exchangeRatesService.getRatesByDate(today)).thenReturn(todayRatesObject);
        Mockito.when(exchangeRatesService.getRatesByDate(yesterday)).thenReturn(yesterdayRatesObject);

        Mockito.when(giphyService.getGif("eur")).thenReturn(mapper.toGifDto(gifObjectsRich[0]));
        Mockito.when(giphyService.getGif("usd")).thenReturn(mapper.toGifDto(gifObjectsBroke[0]));
        Mockito.when(giphyService.getGif("unknown_currency")).thenThrow(new NoSuchCurrency("???"));

    }

    @Test
    void getGifRich() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/gif/eur")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("error", is(false)))
                .andExpect(jsonPath("data.url", is("rich")));
    }

    @Test
    void getGifBroke() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/gif/usd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("error", is(false)))
                .andExpect(jsonPath("data.url", is("broke")));

    }


    @Test
    void getGifError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/gif/unknown_currency")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("error", is(true)));
    }

}