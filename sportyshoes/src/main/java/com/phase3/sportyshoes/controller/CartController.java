package com.phase3.sportyshoes.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.phase3.sportyshoes.dao.PurchaseRepository;
import com.phase3.sportyshoes.dao.UserRepository;
import com.phase3.sportyshoes.global.GlobalData;
import com.phase3.sportyshoes.model.Product;
import com.phase3.sportyshoes.model.Purchase;
import com.phase3.sportyshoes.model.User;
import com.phase3.sportyshoes.service.ProductService;

@Controller
public class CartController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ProductService productService;
    
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id)
    {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model)
    {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index)
    {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model)
    {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @PostMapping("/payNow")
    public String orderPlaced(Model model, Principal principal)
    {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        String userName = principal.getName(); 
        User user = userRepository.getUserByUserName(userName);
        List<Purchase> purchaseList = new ArrayList<>();

        for(Product product : GlobalData.cart)
        {
            Purchase purchase = new Purchase();
            purchase.setProductId(product.getId());
            purchase.setUserId(user.getId());
            purchase.setOrderDate(LocalDate.now());
            purchaseList.add(purchase);
        }

        int n = 1000 + new Random().nextInt(9000);
        model.addAttribute("Receipt ", n);
        model.addAttribute("products", GlobalData.cart);
        purchaseRepository.saveAll(purchaseList);
        return "order-placed";
    }
}
