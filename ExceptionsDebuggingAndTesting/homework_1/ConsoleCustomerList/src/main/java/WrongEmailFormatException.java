@SuppressWarnings("unused")
public class WrongEmailFormatException extends RuntimeException {
    public WrongEmailFormatException() {
        super();
    }
    public WrongEmailFormatException(String message) {
        super(message);
    }
    public WrongEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
