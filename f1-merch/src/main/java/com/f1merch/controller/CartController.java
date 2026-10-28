package com.f1merch.controller;

import com.f1merch.model.OrderForm;
import com.f1merch.model.Product;
import com.f1merch.service.CartService;
import com.f1merch.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cartService);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam Long productId,
                      @RequestParam(required = false) String size,
                      @RequestParam(defaultValue = "1") int quantity,
                      RedirectAttributes redirect) {
        Product product = productService.findById(productId).orElse(null);
        if (product == null) return "redirect:/catalog";

        if (product.hasSizes() && (size == null || !product.getSizes().contains(size))) {
            redirect.addFlashAttribute("error", "Выберите размер");
            return "redirect:/product/" + productId;
        }
        cartService.add(product, product.hasSizes() ? size : null, quantity);
        redirect.addFlashAttribute("message", "«" + product.getName() + "» добавлен в корзину");
        return "redirect:/product/" + productId;
    }

    @PostMapping("/cart/update")
    public String update(@RequestParam String key, @RequestParam int quantity) {
        cartService.updateQuantity(key, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam String key) {
        cartService.remove(key);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (cartService.isEmpty()) return "redirect:/cart";
        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("cart", cartService);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute("orderForm") OrderForm form,
                             BindingResult result, Model model, RedirectAttributes redirect) {
        if (cartService.isEmpty()) return "redirect:/cart";
        if (result.hasErrors()) {
            model.addAttribute("cart", cartService);
            return "checkout";
        }
        String orderNumber = "F1-" + ThreadLocalRandom.current().nextInt(100000, 999999);
        redirect.addFlashAttribute("orderNumber", orderNumber);
        redirect.addFlashAttribute("customerName", form.getFullName());
        redirect.addFlashAttribute("orderTotal", cartService.getTotal());
        cartService.clear();
        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String success(Model model) {
        if (!model.containsAttribute("orderNumber")) return "redirect:/";
        return "success";
    }
}
