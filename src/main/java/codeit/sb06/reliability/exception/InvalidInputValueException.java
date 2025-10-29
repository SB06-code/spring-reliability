package codeit.sb06.reliability.exception;

public class InvalidInputValueException extends BusinessException {
    public InvalidInputValueException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}
