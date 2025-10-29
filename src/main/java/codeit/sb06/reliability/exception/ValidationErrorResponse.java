package codeit.sb06.reliability.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ValidationErrorResponse extends ErrorResponse {

    private final List<FieldErrorDetails> errors;

    private ValidationErrorResponse(final ErrorCode code, final List<FieldErrorDetails> errors) {
        super(code);
        this.errors = errors;
    }

    public static ValidationErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ValidationErrorResponse(code, FieldErrorDetails.of(bindingResult));
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FieldErrorDetails {
        private final String field;
        private final String value;
        private final String reason;

        private static List<FieldErrorDetails> of(final BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldErrorDetails(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }
}
