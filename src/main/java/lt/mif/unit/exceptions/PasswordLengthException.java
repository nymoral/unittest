package lt.mif.unit.exceptions;

/**
 * This exception is thrown is password does not meet the length requirements.
 */
public class PasswordLengthException extends ValidationException {
    public PasswordLengthException(String msg) {
        super(msg);
    }
}
