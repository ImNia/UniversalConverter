package com.converter;

import com.converter.parcing.ConversionRuleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

import static com.converter.parcing.ParcingFile.parcingFile;

@SpringBootApplication
public class Application {
    @Autowired
    public static ArrayList<ConversionRuleImpl> allRules;
    //TODO Java Code Conventions
    public static void main(String[] args) {
        allRules = new ArrayList<>();
        //TODO файл
        allRules = parcingFile("dataEng.csv");
        SpringApplication.run(Application.class, args);

//        if (args.length == 0) {
//            System.out.println("Not have file");
//        } else if (args.length == 1)

//        }
    }
}
