package codeit.sb06.reliability.controller;

import codeit.sb06.reliability.dto.ProductCreateRequest;
import codeit.sb06.reliability.dto.ProductResponse;
import codeit.sb06.reliability.dto.ProductUpdateRequest;
import codeit.sb06.reliability.entity.Product;
import codeit.sb06.reliability.exception.ErrorCode;
import codeit.sb06.reliability.exception.ErrorResponse;
import codeit.sb06.reliability.exception.InvalidInputValueException;
import codeit.sb06.reliability.exception.ValidationErrorResponse;
import codeit.sb06.reliability.service.ProductService;
import codeit.sb06.reliability.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        log.info("상품 등록 요청 수신: {}", request);
        Product newProduct = productService.createProduct(request);
        log.info("신규 상품 등록 완료: {}", newProduct);
        return ResponseEntity.ok(ProductResponse.from(newProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        if (productId == null) {
            throw new InvalidInputValueException("수정할 상품의 ID가 제공되지 않았습니다.");
        }
        log.info("상품 수정 요청 수신: {}", request);
        Product updatedProduct = productService.updateProduct(productId, request);
        log.info("상품 수정 완료: {}", updatedProduct);
        return ResponseEntity.ok(ProductResponse.from(updatedProduct));
    }

    @PatchMapping("/{productId}/decrease-stock")
    public ResponseEntity<Void> decreaseStock(@PathVariable long productId, @RequestParam int quantity) {

        log.info("재고 감소 요청 수신: productId={}, quantity={}", productId, quantity);

        if (quantity <= 0) {
            throw new InvalidInputValueException("재고 감소량은 0보다 커야 합니다.");
        }

        productService.decreaseStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException", e);
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        final ValidationErrorResponse response = ValidationErrorResponse.of(errorCode, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
