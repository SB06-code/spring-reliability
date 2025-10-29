package codeit.sb06.reliability.exception;

import lombok.Getter;

@Getter
public class DuplicateProductNameException extends BusinessException {

    private final String productName;

    public DuplicateProductNameException(String productName) {
        super(ErrorCode.DUPLICATE_PRODUCT_NAME);
        this.productName = productName;
    }
}
