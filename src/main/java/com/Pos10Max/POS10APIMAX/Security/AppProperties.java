package com.Pos10Max.POS10APIMAX.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Autowired
    public Environment env;


    public String getTokenSecret()
    {
        return env.getProperty("jwttokensecret");
    }


}