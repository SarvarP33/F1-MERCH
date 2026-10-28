package com.f1merch.controller;

import com.f1merch.model.Category;
import com.f1merch.model.Team;
import com.f1merch.service.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Данные, нужные на каждой странице (счётчик корзины, списки для меню). */
@ControllerAdvice
public class GlobalModelAdvice {

    private final CartService cartService;

    public GlobalModelAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartCount")
    public int cartCount() { return cartService.getCount(); }

    @ModelAttribute("teams")
    public Team[] teams() { return Team.values(); }

    @ModelAttribute("categories")
    public Category[] categories() { return Category.values(); }
}
