package com.converter.parcing;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParcingFile {
    //TODO Generic??
    private static ArrayList<ConversionRuleImpl> addDependValue(ArrayList<ConversionRuleImpl> arrayRules) {
        ArrayList<ConversionRuleImpl> arrayRulesTmp = new ArrayList(arrayRules);
        for (int i = 0; i < 5; i++) {
            arrayRules = (ArrayList<ConversionRuleImpl>)arrayRulesTmp.clone();
            for (ConversionRuleImpl currentRuleFrom : arrayRules) {
                for (ConversionRuleImpl currentRuleTo : arrayRules) {
                    ConversionRuleImpl desiredObj = new ConversionRuleImpl(
                            currentRuleFrom.getFromValue(),
                            currentRuleTo.getToValue(),
                            currentRuleFrom.getValue() * currentRuleTo.getValue()
                    );
                    boolean containsRule = false;
                    for (ConversionRuleImpl tmp: arrayRulesTmp) {
                        if (tmp.equals(desiredObj)) {
                            containsRule = true;
                            break;
                        }
                    }
                    if (currentRuleFrom.getToValue().equals(currentRuleTo.getFromValue()) &&
                            //Чтобы исключить что добавляются одинаковые значения (мин,мин,1.0)
                            !currentRuleFrom.getFromValue().equals(currentRuleTo.getToValue()) && !containsRule) {
                        arrayRulesTmp.add(new ConversionRuleImpl(currentRuleFrom.getFromValue(), currentRuleTo.getToValue(),
                                currentRuleFrom.getValue() * currentRuleTo.getValue()));
                    }
                }
            }
        }// while (!arrayRules.containsAll(arrayRulesTmp));
        return arrayRulesTmp;
    }

    public static ArrayList<ConversionRuleImpl> parcingFile(String filePath) {
        //TODO Кодировка
        ArrayList<ConversionRuleImpl> arrayRules = new ArrayList();
        char csvSplitBy = ',';
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath), csvSplitBy);
            String[] stringOfData;
            while((stringOfData = reader.readNext()) != null) {
                if (stringOfData.length == 3) {
                    ConversionRuleImpl getRuleUnit = new ConversionRuleImpl(stringOfData[0], stringOfData[1],
                            Double.parseDouble(stringOfData[2]));
                    //TODO А если не прямо зависят
                    ConversionRuleImpl getRuleUnitReverse = new ConversionRuleImpl(stringOfData[1], stringOfData[0],
                            1 / Double.parseDouble(stringOfData[2])); //TODO точность
                    arrayRules.add(getRuleUnit);
                    arrayRules.add(getRuleUnitReverse);
                } else {
                    System.out.println("Параметры не верные!");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        ArrayList<ConversionRuleImpl> arrayRulesDepend = new ArrayList();
        arrayRulesDepend = addDependValue(arrayRules);
        for (ConversionRuleImpl tmp: arrayRulesDepend) {
            System.out.printf("%s\t%s\t%s\t", tmp.getFromValue(), tmp.getToValue(), tmp.getValue());
            System.out.println();
        }
        System.out.println();
        return arrayRules;
    }
}
