package geekbrains.home.des.springleveltwo.domain.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sell {
    private Long sellId;

    private LocalDateTime created;

    private Long orderID;

    private String username;

    private String productTitle;

    private Long amount;

    private BigDecimal sum;
}
