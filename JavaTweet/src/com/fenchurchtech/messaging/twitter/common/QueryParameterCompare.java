package com.fenchurchtech.messaging.twitter.common;

import java.util.Comparator;

public class QueryParameterCompare implements Comparator<QueryParameter>
{
    @Override
    public int compare(QueryParameter o1, QueryParameter o2) {
        if (o1.getName().equals(o2.getName()))
        {
            return o1.getValue().compareTo(o2.getValue());
        }
        else
        {
            return o1.getName().compareTo(o2.getName());
        }
    }
}