package geekbrains.home.des.springleveltwo.dao;

import geekbrains.home.des.springleveltwo.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketDAO extends JpaRepository<Bucket, Long> {
}
