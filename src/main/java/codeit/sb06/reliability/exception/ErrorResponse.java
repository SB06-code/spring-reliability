package codeit.sb06.reliability.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final String message;
    private final String code;
    private final Map<String, Object> details;

    protected ErrorResponse(final ErrorCode code) {
        this(code, new HashMap<>());
    }

    protected ErrorResponse(final ErrorCode code, final Map<String, Object> details) {
        this.message = code.getMessage();
        this.code = code.getCode();
        this.details = details;
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, Map<String, Object> details) {
        return new ErrorResponse(code, details);
    }
}
