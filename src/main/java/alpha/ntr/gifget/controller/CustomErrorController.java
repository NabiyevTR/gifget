package alpha.ntr.gifget.controller;

import alpha.ntr.gifget.responses.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/api/error")
    public ErrorResponse handleError() {
        return new ErrorResponse("Not allowed.");
    }
}
