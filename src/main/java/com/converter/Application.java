package com.converter;

import com.converter.parcing.ConversionRuleImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

import static com.converter.parcing.ParcingFile.parcingFile;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ArrayList<ConversionRuleImpl> allRules = new ArrayList<>();
        allRules = parcingFile("data.csv");
        SpringApplication.run(Application.class, args);

//        if (args.length == 0) {
//            System.out.println("Not have file");
//        } else if (args.length == 1)

//        }
    }
}
