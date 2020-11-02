package geekbrains.home.des.springleveltwo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_details_tbl")
public class OrderDetail {
    private static final String SEQUANCE_NAME = "orderDetails_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUANCE_NAME)
    @SequenceGenerator(name = SEQUANCE_NAME, sequenceName = SEQUANCE_NAME, allocationSize = 1)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount_fld")
    private BigDecimal amount;

    @Column(name = "price_fld")
    private BigDecimal price;

    public OrderDetail(Order order, Product product, Long amount) {
        this.order = order;
        this.product = product;
        this.amount = new BigDecimal(amount);
        this.price = new BigDecimal(product.getPrice());
    }
}
