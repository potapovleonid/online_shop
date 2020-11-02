package geekbrains.home.des.springleveltwo.domain.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {
    private Long purchaseID;

    private List<Sell> sells;

    public List<Sell> getSells() {
        return sells;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseID=" + purchaseID +
                ", sells=" + sells +
                '}';
    }
}
