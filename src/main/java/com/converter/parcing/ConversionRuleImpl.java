package com.converter.parcing;

public class ConversionRuleImpl implements ConversionRule{
    private String srcValue;
    private String dstValue;
    private double value;
    public ConversionRuleImpl(String srcValue, String dstValue, double value) {
        this.srcValue = srcValue;
        this.dstValue = dstValue;
        this.value = value;
    }
    public  ConversionRuleImpl() {}

}
