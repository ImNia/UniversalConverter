package com.converter.handler;

import com.converter.parcing.ConversionRuleImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.MathContext;

import static com.converter.Application.allRules;

public class Converter {
    static MathContext context = new MathContext(5);
    //Когда нет деления
    private static double calculationValue(String[] dataExpFrom, String[] dataExpTo) {
//        double resultExp = 1;
        BigDecimal resultExp = new BigDecimal(1.0);
        //TODO регистры
//        System.out.println(dataExpFrom[0] + " : " + dataExpTo[0]);
        for (int i = 0; i < dataExpFrom.length; i++) {
            for (ConversionRuleImpl currentRule : allRules) {
//                System.out.println(currentRule.getFromValue() + " : " + currentRule.getToValue() + " : " + currentRule.getValue());
                if (currentRule.getFromValue().equals(dataExpFrom[i].trim())) {
                    if (currentRule.getToValue().equals(dataExpTo[i].trim())) {
//                        System.out.println(currentRule.getFromValue() + " : " + currentRule.getToValue() + " : " + currentRule.getValue());
                        System.out.println(resultExp + " * " + currentRule.getValue()
                                    + " : " + BigDecimal.valueOf(currentRule.getValue()));
                        resultExp = resultExp.multiply(BigDecimal.valueOf(currentRule.getValue()), context);
                        break;
                    }
                }
            }
        }
        System.out.println("Rsult: " + resultExp);
        return resultExp.doubleValue();
    }

    //Когда есть деление
    private static double calculationValue(String[] dataExpFromNumerator, String[] dataExpFromDenumirator,
                                         String[] dataExpToNumerator, String[] dataExpToDenumirator) {
        BigDecimal resultExp = new BigDecimal(1.0);
//        System.out.println(resultExp + " : " + calculationValue(dataExpFromNumerator, dataExpToDenumirator));
        resultExp = resultExp.multiply(BigDecimal.valueOf(calculationValue(dataExpFromNumerator, dataExpToNumerator)), context);
//        System.out.println(resultExp + " : " + calculationValue(dataExpFromNumerator, dataExpToDenumirator));
        resultExp = resultExp.divide(BigDecimal.valueOf(calculationValue(dataExpFromDenumirator, dataExpToDenumirator)), context);
//        System.out.println(resultExp);
        return resultExp.doubleValue();
    }

    public static boolean checkValueExist(String[] checkValue) {
        int countCheck = 0;
        for (String currentValue: checkValue) {
            for (ConversionRuleImpl currentRule : allRules) {
                if (currentRule.getFromValue().equals(currentValue.trim())) {
                    System.out.println("asd: " + currentValue);
                    countCheck++;
                    break;
                }
            }
        }
        return countCheck == checkValue.length;
    }

    public static double converterValue(SenderStruct getMessage) {
        String[] dataDivideFrom = getMessage.getFrom().split("/");
        String[] dataDivideTo = getMessage.getTo().split("/");

        if (checkValueExist(dataDivideFrom) && checkValueExist(dataDivideTo)) {
            //TODO могут быть не одинаковые
            switch (dataDivideFrom.length) {
                case 1:
                    return calculationValue(dataDivideFrom[0].split("\\*"), dataDivideTo[0].split("\\*"));
                case 2:
                    return calculationValue(dataDivideFrom[0].split("\\*"), dataDivideFrom[1].split("\\*"),
                            dataDivideTo[0].split("\\*"), dataDivideTo[1].split("\\*"));
                default:
                    System.out.println("Что-то пошло не так");
                    break;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return 0;
    }
}
