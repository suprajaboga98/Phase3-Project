package com.phase3.sportyshoes.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phase3.sportyshoes.dao.UserRepository;
import com.phase3.sportyshoes.dto.ProductDTO;
import com.phase3.sportyshoes.global.PurchaseReport;
import com.phase3.sportyshoes.model.Category;
import com.phase3.sportyshoes.model.Product;
import com.phase3.sportyshoes.model.Purchase;
import com.phase3.sportyshoes.model.User;
import com.phase3.sportyshoes.service.CategoryService;
import com.phase3.sportyshoes.service.ProductService;
import com.phase3.sportyshoes.service.PurchaseService;

@Controller
public class AdminController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductService productService;

    @Autowired
    UserRepository userRepository;

    public static String uploadDir = System.getProperty("user.dir") + "/sportyshoes/src/main/resources/static/productImages";

    @GetMapping("/admin")
    public String adminHome()
    {
        return "admin-home";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model)
    {
        model.addAttribute("category", new Category());
        return "add-categories";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute Category category)
    {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String delCategories(@PathVariable int id)
    {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable int id, Model model)
    {
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent())
        {
            model.addAttribute("category", category.get());
            return "add-categories";
        }
        else
            return "404";
    }

    @GetMapping("/admin/products")
    public String getProducts(Model model)
    {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model)
    {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-products";
    }

    @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile file,
                                  @RequestParam("imgName") String imgName) throws IOException
                                  {
                                      Product product = new Product();
                                      product.setId(productDTO.getId());
                                      product.setName(productDTO.getName());
                                      product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
                                      product.setPrice(productDTO.getPrice());
                                      product.setWeight(productDTO.getWeight());
                                      product.setDescription(productDTO.getDescription());
                                      String imageUUID;
                                      if(!file.isEmpty())
                                      {
                                          imageUUID = file.getOriginalFilename();
                                          Path path = Paths.get(uploadDir, imageUUID);
                                          Files.write(path, file.getBytes());
                                      }
                                      else
                                          imageUUID = imgName;
                                      product.setImageName(imageUUID);
                                      productService.addProduct(product);
                                      return "redirect:/admin/products";
                                  }

    @GetMapping("/admin/products/update/{id}")
    public String updateProduct(@PathVariable long id, Model model)
    {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "add-products";    
    }

    @GetMapping("/admin/purchaseReport")
    public String purchaseReport(Model model)
    {
        List<Purchase> purchaseList = purchaseService.getAllPurchases();
        List<PurchaseReport> purchaseReportList = new ArrayList<>();

        for(Purchase purchase : purchaseList)
        {
            Product product = new Product();
            User user = new User();
            Category category = new Category();
            PurchaseReport purchaseReport = new PurchaseReport();
            Long productId = purchase.getProductId();
            int userId = purchase.getUserId();

            product = productService.getProductById(productId).get();
            user = userRepository.findById(userId).get();
            category = product.getCategory();

            purchaseReport.setName(user.getFirstName());
            purchaseReport.setEmail(user.getEmail());
            purchaseReport.setProductId(product.getId());
            purchaseReport.setProductName(product.getName());
            purchaseReport.setPrice(product.getPrice());
            purchaseReport.setCategory(category.getName());
            purchaseReport.setDate(purchase.getOrderDate().toString());

            purchaseReportList.add(purchaseReport);
        }
        model.addAttribute("purchaseList", purchaseReportList);
        return "purchase-report";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model)
    {
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        model.addAttribute("userlist", userList);
        return "users";
    }
}
