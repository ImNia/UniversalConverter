package com.converter.handler;

import com.converter.parcing.ConversionRuleImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;

import static com.converter.Application.allRules;

public class Converter {
    static MathContext context = new MathContext(5); //TODO исправить до 15
    //Когда нет деления
    private static double internalCheck(ConversionRuleImpl currentRule, String dataExpFrom, String dataExpTo) {
        if (currentRule.getFromValue().equals(dataExpFrom.trim())) {
            if (currentRule.getToValue().equals(dataExpTo.trim())) {
                return currentRule.getValue();
            }
        }
        return -1.0;
    }

    private static double calculationValue(String[] dataExpFrom, String[] dataExpTo) {
        BigDecimal resultExp = new BigDecimal(1.0);
        boolean conformValue = false;
        double value = 0.0;

        for (int i = 0; i < dataExpFrom.length; i++) {
            System.out.println(dataExpFrom[i]);
            if (dataExpFrom[i].trim().matches("[0-9]+")) {
                System.out.println("Hello, we are in cycle");
                resultExp = resultExp.multiply(BigDecimal.valueOf(Long.parseLong(dataExpFrom[i].trim())));
                conformValue = true;
            } else {
                for (ConversionRuleImpl currentRule : allRules) {
                    for (String dataTo : dataExpTo) {
                        value = internalCheck(currentRule, dataExpFrom[i].toLowerCase(Locale.ROOT), dataTo.toLowerCase(Locale.ROOT));
                        if (value != -1.0) {
                            resultExp = resultExp.multiply(BigDecimal.valueOf(value), context);
                            conformValue = true;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Rsult: " + resultExp);
        if (!conformValue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return resultExp.doubleValue();
    }

    //Когда есть деление
    private static double calculationValue(String[] dataExpFromNumerator, String[] dataExpFromDenumirator,
                                         String[] dataExpToNumerator, String[] dataExpToDenumirator) {
        BigDecimal resultExp = new BigDecimal(1.0);
        resultExp = resultExp.multiply(BigDecimal.valueOf(calculationValue(dataExpFromNumerator, dataExpToNumerator)), context);
        resultExp = resultExp.divide(BigDecimal.valueOf(calculationValue(dataExpFromDenumirator, dataExpToDenumirator)), context);
        return resultExp.doubleValue();
    }

    public static boolean checkValueExist(String[] checkValue) {
        int countCheck = 0;

        System.out.println("check: " + checkValue.length + " / " + checkValue[0]);
        if (checkValue.length == 1 && checkValue[0].trim().matches("[0-9]+")) {
            return true;
        }
        for (String currentValue: checkValue) {
            for (ConversionRuleImpl currentRule : allRules) {
                if (currentRule.getFromValue().equals(currentValue.trim().toLowerCase(Locale.ROOT))) {
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

        boolean check = true;
        for (String valueCheck: dataDivideFrom) {
            check = check && checkValueExist(valueCheck.split("\\*"));
        }
        for (String valueCheck: dataDivideTo) {
            check = check && checkValueExist(valueCheck.split("\\*"));
        }
        if (check) {
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
