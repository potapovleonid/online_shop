package geekbrains.home.des.springleveltwo.controllers;

import geekbrains.home.des.springleveltwo.dto.BucketDTO;
import geekbrains.home.des.springleveltwo.service.BucketService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String aboutBucket(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/bucket";
        } else {
            System.out.println("send delete request product id: " + id);
            bucketService.deleteProduct(id, principal.getName());
        }
        return "redirect:/bucket";
    }

    @PostMapping
    public String commitBucket(Model model, Principal principal){
        if(principal != null){
            bucketService.commitBucketToOrder(principal.getName());
        }
        return "redirect:/bucket";
    }

}
