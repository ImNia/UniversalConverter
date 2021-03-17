package com.converter.handler;

import com.converter.parcing.ConversionRuleImpl;

import static com.converter.Application.allRules;

public class Converter {
    //Когда нет деления
    private static double calculationValue(String[] dataExpFrom, String[] dataExpTo) {
        double resultExp = 1;
        //TODO регистры
        for (int i = 0; i < dataExpFrom.length; i++) {
            int indexValue = 1;
            for (ConversionRuleImpl currentRule: allRules) {
                System.out.println(currentRule.getFromValue() + " : " + currentRule.getToValue() + " : " + currentRule.getValue());
                System.out.println(dataExpFrom[i] + " : " + dataExpTo[i]);
                if (currentRule.getFromValue().trim().equals(dataExpFrom[i].trim())) {
                    if (currentRule.getToValue().trim().equals(dataExpTo[i].trim())) {
                        System.out.println(resultExp + " * " + currentRule.getValue());
                        resultExp *= currentRule.getValue();
                        break;
                    }
                }
            }
            if (indexValue == -1) {
                System.out.println("Такого нет");
                return -1; //404
            }
        }
        System.out.println("Rsult: " + resultExp);
        return resultExp;
    }

    //Когда есть деление
    private static double calculationValue(String[] dataExpFromNumerator, String[] dataExpFromDenumirator,
                                         String[] dataExpToNumerator, String[] dataExpToDenumirator) {
        double resultExp = 1;
        resultExp *= calculationValue(dataExpFromNumerator, dataExpToNumerator);
        resultExp *= calculationValue(dataExpFromDenumirator, dataExpToDenumirator);
        return resultExp;
    }

    public static double converterValue(SenderStruct getMessage) {
        String[] dataDivideFrom = getMessage.getFromValue().split("/");
        String[] dataDivideTo = getMessage.getToValue().split("/");
        //TODO могут быть не идентичны
        switch(dataDivideFrom.length) {
            case 1:
                calculationValue(dataDivideFrom[0].split("\\*"), dataDivideTo[0].split("\\*"));
                break;
            case 2:
                calculationValue(dataDivideFrom[0].split("\\*"), dataDivideFrom[1].split("\\*"),
                        dataDivideTo[0].split("\\*"), dataDivideTo[1].split("\\*"));
                break;
            default:
                System.out.println("Что-то пошло не так");
                break;
        }
        return 0;
    }
}
