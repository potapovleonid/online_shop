package geekbrains.home.des.springleveltwo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "buckets_tbl")
public class Bucket {
    private static final String SEQUANCE_NAME = "bucket_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUANCE_NAME)
    @SequenceGenerator(name = SEQUANCE_NAME, sequenceName = SEQUANCE_NAME, allocationSize = 1)
    @Column(name = "bucket_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "bucket_products",
                joinColumns = @JoinColumn(name = "bucket_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
