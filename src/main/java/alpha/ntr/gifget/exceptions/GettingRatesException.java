package alpha.ntr.gifget.exceptions;

public class GettingRatesException extends RuntimeException{

    public GettingRatesException() {
        super("Cannot load rates from server.");
    }
}
