package com.f1merch.controller;

import com.f1merch.model.Category;
import com.f1merch.model.Product;
import com.f1merch.model.Team;
import com.f1merch.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ShopController {

    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featured", productService.findFeatured());
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(required = false) Team team,
                          @RequestParam(required = false) Category category,
                          @RequestParam(required = false) String q,
                          @RequestParam(required = false, defaultValue = "") String sort,
                          Model model) {
        model.addAttribute("products", productService.search(team, category, q, sort));
        model.addAttribute("selectedTeam", team);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        return "catalog";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        model.addAttribute("product", product);
        model.addAttribute("related", productService.findRelated(product, 4));
        return "product";
    }
}
