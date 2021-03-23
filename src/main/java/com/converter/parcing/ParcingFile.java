package com.converter.parcing;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ParcingFile {
    private static ArrayList<ConversionRuleImpl> addDependValue(ArrayList<ConversionRuleImpl> arrayRules) {
        ArrayList<ConversionRuleImpl> arrayRulesTmp = new ArrayList<>(arrayRules);
        do {
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
        } while (!arrayRules.containsAll(arrayRulesTmp));
        return arrayRulesTmp;
    }

    public static ArrayList<ConversionRuleImpl> parcingFile(String filePath) {
        ArrayList<ConversionRuleImpl> arrayRules = new ArrayList<>();
        char csvSplitBy = ',';
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath), csvSplitBy);
            String[] stringOfData;
            while((stringOfData = reader.readNext()) != null) {
                if (stringOfData.length == 3) {
                    ConversionRuleImpl getRuleUnit = new ConversionRuleImpl(stringOfData[0].trim().toLowerCase(Locale.ROOT),
                            stringOfData[1].trim().toLowerCase(Locale.ROOT), Double.parseDouble(stringOfData[2]));
                    ConversionRuleImpl getRuleUnitReverse = new ConversionRuleImpl(stringOfData[1].trim().toLowerCase(Locale.ROOT),
                            stringOfData[0].trim().toLowerCase(Locale.ROOT), 1.0 / Double.parseDouble(stringOfData[2]));
                    arrayRules.add(getRuleUnit);
                    arrayRules.add(getRuleUnitReverse);
                } else {
                    System.out.println("Параметры не верные!");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        ArrayList<ConversionRuleImpl> arrayRulesDepend = addDependValue(arrayRules);
//        for (ConversionRuleImpl tmp: arrayRulesDepend) {
//            System.out.printf("%s\t%s\t%s\t", tmp.getFromValue(), tmp.getToValue(), tmp.getValue());
//            System.out.println();
//        }
//        System.out.println();
        return arrayRulesDepend;
    }
}
