package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.dao.BucketDAO;
import geekbrains.home.des.springleveltwo.dao.ProductDAO;
import geekbrains.home.des.springleveltwo.domain.*;
import geekbrains.home.des.springleveltwo.dto.BucketDTO;
import geekbrains.home.des.springleveltwo.dto.BucketDetailDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketDAO bucketDAO;
    private final ProductDAO productDAO;
    private final UserService userService;
    private final OrderService orderService;

    public BucketServiceImpl(BucketDAO bucketDAO, ProductDAO productDAO, UserService userService, OrderService orderService) {
        this.bucketDAO = bucketDAO;
        this.productDAO = productDAO;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public Bucket createBucket(User user, List<Long> productsId) {
        Bucket bucket = new Bucket();
        user.setBucket(bucket);
        List<Product> products = getCollectRefProducts(productsId);
        bucket.setProducts(products);
        return bucketDAO.save(bucket);
    }

    private List<Product> getCollectRefProducts(List<Long> ids){
        return ids.stream()
                .map(productDAO::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Bucket bucket, List<Long> productsId) {
        List<Product> products = bucket.getProducts();
        if (products == null){
            products = new ArrayList<>();
        }
        products.addAll(getCollectRefProducts(productsId));
        bucket.setProducts(products);
        bucketDAO.save(bucket);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id, String username) {
        System.out.println("start finder user: " + username + "\nid: " + id);
        Bucket bucket = userService.findByName(username).getBucket();
        System.out.println("Finded Bucket: " + bucket.toString());
        List<Product> products = bucket.getProducts();
        System.out.println(products.toString());
        products.removeIf(p -> p.getId().equals(id));
        System.out.println(products.toString());
        bucket.setProducts(products);
        bucketDAO.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);

        if (user == null || user.getBucket() == null){
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProduct = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products){
            BucketDetailDTO detailDTO = mapByProduct.get(product.getId());
            if (detailDTO == null){
                mapByProduct.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detailDTO.setAmount(detailDTO.getAmount() + 1.0);
                detailDTO.setSum(detailDTO.getSum() + product.getPrice());
            }
        }

        bucketDTO.setBucketDetails(new ArrayList<>(mapByProduct.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    @Override
    @Transactional
    public void commitBucketToOrder(String name) {
        User user = userService.findByName(name);
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucket();
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetail> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetail(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketDAO.save(bucket);
    }
}
