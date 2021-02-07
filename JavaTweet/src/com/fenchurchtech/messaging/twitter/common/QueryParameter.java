package com.fenchurchtech.messaging.twitter.common;

public class QueryParameter {
    private String _name ="";
    private String _value ="";

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    public QueryParameter(String name, String value)
    {
        this._name = name;
        this._value = value;
    }

}

