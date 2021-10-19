package alpha.ntr.gifget.model.service.gif;

import alpha.ntr.gifget.exceptions.GettingRatesException;
import alpha.ntr.gifget.exceptions.NoSuchCurrency;
import alpha.ntr.gifget.model.dto.GifDto;

public interface GifService {
    GifDto getGif(String currencyCode) throws GettingRatesException, NoSuchCurrency;
}
