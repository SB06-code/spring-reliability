package codeit.sb06.reliability.controller;

import codeit.sb06.reliability.dto.ProductCreateRequest;
import codeit.sb06.reliability.dto.ProductResponse;
import codeit.sb06.reliability.entity.Product;
import codeit.sb06.reliability.exception.InvalidInputValueException;
import codeit.sb06.reliability.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {

        if (request.price() < 0) {
            throw new InvalidInputValueException("상품 가격은 0보다 작을 수 없습니다.");
        }

        if (request.stock() < 0) {
            throw new InvalidInputValueException("재고는 0보다 작을 수 없습니다.");
        }

        Product newProduct = productService.createProduct(request);
        return ResponseEntity.ok(ProductResponse.from(newProduct));
    }

    @PatchMapping("/{productId}/decrease-stock")
    public ResponseEntity<Void> decreaseStock(@PathVariable long productId, @RequestParam int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputValueException("재고 감소량은 0보다 커야 합니다.");
        }

        productService.decreaseStock(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
