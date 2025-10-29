package codeit.sb06.reliability.dto;

import codeit.sb06.reliability.entity.Product;

public record ProductResponse(
        long id,
        String name,
        long price,
        int stock
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
