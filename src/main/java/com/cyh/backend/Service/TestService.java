package com.cyh.backend.Service;


import com.cyh.backend.Dao.DrinkDao;
import com.cyh.backend.Logic.TestLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {

    @Autowired
    TestLogic testLogic;

    @RequestMapping("/test")
    public String method1(){
        return testLogic.genPdfHashByGrantCode();
    }
}
