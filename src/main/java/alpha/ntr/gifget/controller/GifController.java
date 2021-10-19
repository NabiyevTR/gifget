package alpha.ntr.gifget.controller;

import alpha.ntr.gifget.exceptions.GettingRatesException;
import alpha.ntr.gifget.exceptions.NoSuchCurrency;
import alpha.ntr.gifget.model.service.gif.GifService;
import alpha.ntr.gifget.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/gif")
@RequiredArgsConstructor
public class GifController {

    private final GifService gifService;

    @GetMapping("/{currencyCode}")
    public Response getGif(@PathVariable("currencyCode") String currencyCode) throws GettingRatesException, NoSuchCurrency {
        return  new Response( gifService.getGif(currencyCode));

    }
}
