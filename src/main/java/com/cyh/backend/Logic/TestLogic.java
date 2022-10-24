package com.cyh.backend.Logic;

import com.cyh.backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestLogic {

    @Autowired
    FileUtil fileUtil;

    public String genPdfHashByGrantCode(){

        return null;
    }
}
