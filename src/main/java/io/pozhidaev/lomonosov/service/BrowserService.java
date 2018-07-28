package io.pozhidaev.lomonosov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BrowserService {
    //todo  /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --headless --disable-gpu --dump-dom http://www.yad2.co.il/Nadlan/rent.php 1>> a.t 2>> b.t


    public void createChromeProcess(){
        ProcessBuilder processBuilder = new ProcessBuilder();
    }

    public void loadPage(){

    }




}
