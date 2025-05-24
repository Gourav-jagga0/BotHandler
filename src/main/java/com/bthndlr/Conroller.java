package com.bthndlr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bthndlr.data.IDataGenerator;

@RestController
public class Conroller {
    @Autowired
    IDataGenerator dataGenerator;

    @GetMapping("/data")
    public String getdata () {
        return dataGenerator.getStrData();
    }
    
}
