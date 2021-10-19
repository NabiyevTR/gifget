package alpha.ntr.gifget.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {

    private boolean error = false;
    private String errorMessage = "";
    private Object data;

    public Response(Object data) {
        this.data = data;
    }
}



