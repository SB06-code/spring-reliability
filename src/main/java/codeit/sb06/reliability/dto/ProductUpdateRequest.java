package codeit.sb06.reliability.dto;

import codeit.sb06.reliability.validation.ProductName;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @Size(max = 100, message = "상품명은 100자를 넘을 수 없습니다.")
        @ProductName
        String name,

        @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
        Long price,

        @PositiveOrZero(message = "재고는 0 이상이어야 합니다.")
        Integer stock
) {

}
