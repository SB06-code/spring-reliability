package codeit.sb06.reliability.service;

import codeit.sb06.reliability.dto.ProductCreateRequest;
import codeit.sb06.reliability.entity.Product;
import codeit.sb06.reliability.exception.DuplicateProductNameException;
import codeit.sb06.reliability.exception.InsufficientStockException;
import codeit.sb06.reliability.exception.ProductNotFoundException;
import codeit.sb06.reliability.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(ProductCreateRequest request) {

        log.debug("상품 생성 시작: name={}, price={}, stock={}", request.name(), request.price(), request.stock());

        checkDuplicateName(request.name());

        Product product = Product.of(request.name(), request.price(), request.stock());

        try {
            Product savedProduct = productRepository.save(product);
            log.debug("상품 생성 완료: id={}", savedProduct.getId());
            return savedProduct;
        } catch (DataIntegrityViolationException e) {
            log.warn("상품명 중복: {}", request.name(), e);
            throw new DuplicateProductNameException(request.name());
        }
    }

    private void checkDuplicateName(String name) {
        var nameExists = productRepository.existsByName(name);
        if (nameExists) {
            throw new DuplicateProductNameException(name);
        }
    }

    @Transactional
    public void decreaseStock(long productId, int quantity) {

        log.debug("재고 감소 서비스 시작: productId={}, quantity={}", productId, quantity);

        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (product.getStock() < quantity) {
            log.warn("재고 부족 발생: productId={}, available={}, requested={}", productId, product.getStock(), quantity);
            throw new InsufficientStockException();
        }

        product.decreaseStock(quantity);

        productRepository.save(product);

        log.debug("재고 감소 완료: productId={}, stock={}", productId, product.getStock());
    }
}
