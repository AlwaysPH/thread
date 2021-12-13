package com.ph.thread.activeObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activeObject")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    @GetMapping("/getData")
    public String getData(){
        return activeService.getData();
    }
}
