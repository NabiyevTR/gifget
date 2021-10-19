package alpha.ntr.gifget.model.service.gif.giphy;

import alpha.ntr.gifget.model.dto.GifDto;
import org.springframework.stereotype.Component;

@Component
public class GifMapper {

    public GifDto toGifDto(GifObject gifObjectDto) {
        return GifDto.builder()
                .url(gifObjectDto.getUrl())
                .bitlyUrl(gifObjectDto.getBitly_url())
                .embedUrl(gifObjectDto.getEmbed_url())
                .build();
    }
}
