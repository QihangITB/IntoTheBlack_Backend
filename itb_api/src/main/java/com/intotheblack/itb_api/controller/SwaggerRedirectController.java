package com.intotheblack.itb_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerRedirectController {
    
    // En caso de acceder a 'http://localhost:8080', te redirecciona al Swagger.
    @GetMapping("/")
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
