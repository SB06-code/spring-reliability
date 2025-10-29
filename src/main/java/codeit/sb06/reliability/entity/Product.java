package codeit.sb06.reliability.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private long price;

    private int stock;

    private Product(String name, long price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product of(String name, long price, int stock) {
        return new Product(name, price, stock);
    }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }
}
