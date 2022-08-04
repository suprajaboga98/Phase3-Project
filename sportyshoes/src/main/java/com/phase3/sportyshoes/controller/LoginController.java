package com.phase3.sportyshoes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phase3.sportyshoes.dao.RoleRepository;
import com.phase3.sportyshoes.dao.UserRepository;
import com.phase3.sportyshoes.global.GlobalData;
import com.phase3.sportyshoes.model.Role;
import com.phase3.sportyshoes.model.User;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @RequestMapping("/login")
    public String login()
    {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user , Model model) throws ServletException 
    {	
		List<Role>roles = new ArrayList<Role>();
		roles.add(roleRepository.findById(1).get());
		user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // user.setEnabled(true);
		userRepository.save(user);
        model.addAttribute("user",new User());
		return "register";	
    }
}
