package alpha.ntr.gifget.exceptions;

import alpha.ntr.gifget.responses.ErrorResponse;
import alpha.ntr.gifget.responses.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {
            GettingRatesException.class,
            NoSuchCurrency.class,
    })
    protected ResponseEntity<Object> handleExchangeRatesError(RuntimeException ex, WebRequest request) {
        Response response = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = {
            Exception.class
    })
    protected ResponseEntity<Object> handleServerErrors(RuntimeException ex, WebRequest request) {
        Response response = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
