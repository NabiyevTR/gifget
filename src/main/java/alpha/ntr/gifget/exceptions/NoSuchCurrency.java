package alpha.ntr.gifget.exceptions;

public class NoSuchCurrency extends RuntimeException{

    public NoSuchCurrency(String currency) {
        super("Currency " + currency + " is not allowed");
    }
}
