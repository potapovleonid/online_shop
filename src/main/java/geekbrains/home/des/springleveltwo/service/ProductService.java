package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.domain.Product;
import geekbrains.home.des.springleveltwo.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    ProductDTO getById(Long id);
    void deleteById(Long id);
    void addToUserBucket(Long productId, String username);
    void addProduct(ProductDTO productDTO);
}
