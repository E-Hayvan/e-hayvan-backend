package com.production.ehayvanbackendapi.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "login"; // Assuming your login page is named "login.html" and it is located in the "src/main/resources/templates" directory
    }
}
