package codeit.sb06.reliability.exception;


public class InsufficientStockException extends BusinessException {
    public InsufficientStockException() {
        super(ErrorCode.INSUFFICIENT_STOCK);
    }
}