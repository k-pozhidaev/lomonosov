package io.jedaev.lomonosov.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class SecuritiesInit implements CommandLineRunner{


    @Override
    public void run(String... strings) throws Exception {

    }

    private int loadSecurities(){
        return -1;
    }

}
