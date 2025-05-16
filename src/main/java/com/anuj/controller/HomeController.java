package com.anuj.controller;

import com.anuj.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ApiResponse HomeControllerHandler(){
        ApiResponse apiResponce= new ApiResponse();
        apiResponce.setMessage("welcome  to ecom multivender system api");
        return apiResponce;
    }
}
