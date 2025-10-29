package codeit.sb06.reliability.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(500, "C002", "서버 내부 오류가 발생했습니다."),

    // Product
    PRODUCT_NOT_FOUND(404, "P001", "해당 상품을 찾을 수 없습니다."),
    INSUFFICIENT_STOCK(400, "P002", "상품 재고가 부족합니다."),
    DUPLICATE_PRODUCT_NAME(400, "P003", "이미 등록된 상품명입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
