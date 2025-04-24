package com.anuj.controller;

import com.anuj.responce.ApiResponce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ApiResponce HomeControllerHandler(){
        ApiResponce apiResponce= new ApiResponce();
        apiResponce.setMessage("welcome  to ecom multivender system api");
        return apiResponce;
    }
}
