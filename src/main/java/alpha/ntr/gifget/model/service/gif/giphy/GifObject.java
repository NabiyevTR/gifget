package alpha.ntr.gifget.model.service.gif.giphy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GifObject {
    private String type;
    private String id;
    private String slug;
    private String url;
    private String bitly_url;
    private String embed_url;
    private String username;
    private String source;
    private String rating;
    private String content_url;
    private String source_tld;
    private String source_post_url;
    private String update_datetime;
    private String create_datetime;
    private String import_datetime;
    private String trending_datetime;
    private String title;


}
