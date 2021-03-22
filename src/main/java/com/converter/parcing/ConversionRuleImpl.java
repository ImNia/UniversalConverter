package com.converter.parcing;

import java.util.Objects;

public class ConversionRuleImpl implements ConversionRule{
    private String fromValue;
    private String toValue;
    private double value;

    public ConversionRuleImpl(String srcValue, String dstValue, double value) {
        this.fromValue = srcValue;
        this.toValue = dstValue;
        this.value = value;
    }
    public  ConversionRuleImpl() {}

    public String getFromValue() {
        return fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public double getValue() {
        return (double)Math.round(value * 100000d) / 100000d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionRuleImpl that = (ConversionRuleImpl) o;
        //Убрала сравнение значений
        return Objects.equals(fromValue, that.fromValue) && Objects.equals(toValue, that.toValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromValue, toValue, value);
    }
}
