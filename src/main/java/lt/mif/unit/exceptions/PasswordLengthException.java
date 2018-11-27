package lt.mif.unit.exceptions;

public class PasswordLengthException extends ValidationException {
    public PasswordLengthException(String msg) {
        super(msg);
    }
}
