package com.converter.handler;

import com.converter.parcing.ConversionRuleImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.converter.Application.allRules;

public class Converter {
    static MathContext context = new MathContext(5);
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
        double value = 0.0;
        for (int i = 0; i < dataExpFrom.length; i++) {
            for (ConversionRuleImpl currentRule : allRules) {
                for (String dataTo: dataExpTo) {
                    value = internalCheck(currentRule, dataExpFrom[i].toLowerCase(Locale.ROOT), dataTo.toLowerCase(Locale.ROOT));
                    if (value != -1.0) {
                        resultExp = resultExp.multiply(BigDecimal.valueOf(value), context);
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
        resultExp = resultExp.multiply(BigDecimal.valueOf(calculationValue(dataExpFromNumerator, dataExpToNumerator)), context);
        resultExp = resultExp.divide(BigDecimal.valueOf(calculationValue(dataExpFromDenumirator, dataExpToDenumirator)), context);
        return resultExp.doubleValue();
    }

    public static boolean checkValueExist(String[] checkValue) {
        int countCheck = 0;

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
