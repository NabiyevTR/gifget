package alpha.ntr.gifget.model.service.gif.giphy;

import feign.Param;
import feign.RequestLine;

public interface GiphyClient {

    @RequestLine("GET /search?api_key={api_key}&q={q}&limit={limit}&offset={offset}&rating={rating}&lang={lang}")
    GiphyResponse searchGif(
            @Param("api_key") String apiKey,
            @Param("q") String query,
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("rating") String rating,
            @Param("lang") String lang
    );


}
