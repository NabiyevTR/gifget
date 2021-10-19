package alpha.ntr.gifget.model.service.exchangerates.openexchangerates;

import feign.Param;
import feign.RequestLine;

public interface OpenExchangeRatesClient {

    @RequestLine("GET /latest.json?app_id={app_id}")
    OpenExchangeRatesObject getLatestRates(
            @Param("app_id") String appId
    );

    @RequestLine("GET /historical/{date}.json?app_id={app_id}")
    OpenExchangeRatesObject getHistoryRates(
            @Param("app_id") String appId,
            @Param("date") String date
    );

    // available for Developer, Enterprise and Unlimited plan clients
    @RequestLine("GET /latest.json/?app_id={app_id}&base={base}")
    OpenExchangeRatesObject getLatestRates(
            @Param("app_id") String appId,
            @Param("base") String base
    );

}
