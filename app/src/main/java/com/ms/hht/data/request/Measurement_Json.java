package com.ms.hht.data.request;

public class Measurement_Json
{

    public String Name;
    public String Value;
    public String Small_icon;
    public String Largeicon;
    public String Measurement_point_Description;
    public String InchValue;
    public String CmValue;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getSmall_icon() {
        return Small_icon;
    }

    public void setSmall_icon(String small_icon) {
        Small_icon = small_icon;
    }

    public String getLargeicon() {
        return Largeicon;
    }

    public void setLargeicon(String largeicon) {
        Largeicon = largeicon;
    }

    public String getMeasurement_point_Description() {
        return Measurement_point_Description;
    }

    public void setMeasurement_point_Description(String measurement_point_Description) {
        Measurement_point_Description = measurement_point_Description;
    }

    public String getInchValue() {
        return InchValue;
    }

    public void setInchValue(String inchValue) {
        InchValue = inchValue;
    }

    public String getCmValue() {
        return CmValue;
    }

    public void setCmValue(String cmValue) {
        CmValue = cmValue;
    }
}
