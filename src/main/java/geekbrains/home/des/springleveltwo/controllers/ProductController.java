package geekbrains.home.des.springleveltwo.controllers;

import geekbrains.home.des.springleveltwo.dto.ProductDTO;
import geekbrains.home.des.springleveltwo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAll(Model model){
        List<ProductDTO> products = productService.getAll();
        model.addAttribute("products", products);
        return "products";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/bucket")
    public String addProductInBucket(@PathVariable Long id, Principal principal){
        if (principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDTO productDTO){
        productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN', 'SUPER_ADMIN')")
    @MessageMapping("/products")
    @SendTo("/topic/products")
    public ProductDTO messageAddProduct(ProductDTO productDTO){
        productService.addProduct(productDTO);
        return productDTO;
    }
}
