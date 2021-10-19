package alpha.ntr.gifget.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GifDto {
    private String url;
    private String bitlyUrl;
    private String embedUrl;
}
