package lt.mif.unit.exceptions;

/**
 * This exception indicates unsatisfied validation requirements.
 */
public class ValidationException extends Exception {
    public ValidationException(String msg) {
        super(msg);
    }
}
