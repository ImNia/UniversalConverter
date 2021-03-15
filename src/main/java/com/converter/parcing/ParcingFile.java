package com.converter.parcing;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParcingFile {
    public static ArrayList<ConversionRuleImpl> parcingFile(String filePath) {
        //TODO Кодировка
        ArrayList<ConversionRuleImpl> arrayRules = new ArrayList();
        char csvSplitBy = ',';
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath), csvSplitBy);
            String[] stringOfData;
            while((stringOfData = reader.readNext()) != null) {
                if (stringOfData.length == 3) {
                    ConversionRuleImpl getRuleUnit = new ConversionRuleImpl(stringOfData[0], stringOfData[1], Double.parseDouble(stringOfData[2]));
                    arrayRules.add(getRuleUnit);
                    for (String tmp: stringOfData) {
                        System.out.printf("%s\t", tmp);
                    }
                    System.out.println();
                } else {
                    System.out.println("Параметры не верные!");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return arrayRules;
    }
}
