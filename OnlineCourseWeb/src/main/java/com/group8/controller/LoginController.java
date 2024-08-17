package com.group8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     *
     * Login Page
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    /**
     *
     * Forbidden Page
     *
     * @return
     */
    @GetMapping("/403")
    public String forbiddenPage() {
        return "403";
    }

    /**
     *
     * Lock Screen Page
     *
     * @return
     */
}
