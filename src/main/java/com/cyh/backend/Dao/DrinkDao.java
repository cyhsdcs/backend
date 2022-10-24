package com.cyh.backend.Dao;



import com.cyh.backend.util.JsonUtil;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@Component
public class DrinkDao {
    private static String drinkList;


    @PostConstruct
    public static String getDrink(){
        try {
            drinkList = JsonUtil.readJsonFile("classpath:static/popular.json");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return drinkList;

    }




}
