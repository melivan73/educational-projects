@SuppressWarnings("unused")
public class WrongPhoneFormatException extends RuntimeException {
    public WrongPhoneFormatException() {
        super();
    }
    public WrongPhoneFormatException(String message) {
        super(message);
    }
    public WrongPhoneFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
