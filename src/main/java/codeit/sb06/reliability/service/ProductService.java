package codeit.sb06.reliability.service;

import codeit.sb06.reliability.dto.ProductCreateRequest;
import codeit.sb06.reliability.entity.Product;
import codeit.sb06.reliability.exception.DuplicateProductNameException;
import codeit.sb06.reliability.exception.InsufficientStockException;
import codeit.sb06.reliability.exception.ProductNotFoundException;
import codeit.sb06.reliability.repository.ProductRepository;
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

        checkDuplicateName(request.name());

        Product product = Product.of(request.name(), request.price(), request.stock());

        try {
            return productRepository.save(product);
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
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (product.getStock() < quantity) {
            throw new InsufficientStockException();
        }

        product.decreaseStock(quantity);

        productRepository.save(product);
    }
}
