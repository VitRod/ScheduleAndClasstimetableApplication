package ua.com.vit.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

    @RequestMapping(value = "/")
    public String showToolbar() {
        return "menu";
    }

}
