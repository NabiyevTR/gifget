package alpha.ntr.gifget.responses;

public class ErrorResponse extends Response {

    public ErrorResponse(String errorMessage) {
        super(null);
        this.setError(true);
        this.setErrorMessage(errorMessage);
    }
}
