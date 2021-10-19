package alpha.ntr.gifget.model.service.exchangerates.openexchangerates;

import lombok.Data;

import java.util.Map;

@Data
public class OpenExchangeRatesObject {
    private String disclaimer;
    private String license;
    private long timestamp;
    private String base;
    private Map<String, Double> rates;
}
