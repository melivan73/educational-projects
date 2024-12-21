@SuppressWarnings("unused")
public class CommandParamsCountException extends RuntimeException {
    public CommandParamsCountException() {
        super();
    }
    public CommandParamsCountException(String message) {
        super(message);
    }
    public CommandParamsCountException(String message, Throwable cause) {
        super(message, cause);
    }
}
