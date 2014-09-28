package com.furkanbayraktar.databinding.common.parser;

import com.furkanbayraktar.databinding.exception.UnsupportedSyntaxError;
import com.furkanbayraktar.databinding.logging.Logger;

import java.util.HashMap;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class Parser {

    private static Parser instance;

    private Parser(){
        Logger.info("Parser object created.");
    }

    public static Parser getInstance(){
        if(instance == null){
            instance = new Parser();
        }
        return instance;
    }

    public HashMap<String, String> parse(String tag){

        try {
            Logger.info("Parsing tag...");

            HashMap<String, String> result = new HashMap<String, String>();

            Logger.info("Looking for assignable values...");

            int start = tag.indexOf("#ASSIGN");

            if (start != -1) {
                int start2 = tag.indexOf("{", start) + 1;
                int end = tag.indexOf("}", start2);

                if (end == -1) {
                    UnsupportedSyntaxError ex = new UnsupportedSyntaxError();
                    Logger.error("Unsupported syntax.", ex);
                }

                result.put("assign", tag.substring(start2, end));

                Logger.info("Assignable values found: " + result.get("assign"));
            }

            Logger.info("Looking for visibility values...");

            start = tag.indexOf("#VISIBLE");

            if (start != -1) {
                int start2 = tag.indexOf("{", start) + 1;
                int end = tag.indexOf("}", start2);

                if (end == -1) {
                    UnsupportedSyntaxError ex = new UnsupportedSyntaxError();
                    Logger.error("Unsupported syntax.", ex);
                }

                result.put("visible", tag.substring(start2, end));

                Logger.info("Visibility values found: " + result.get("visible"));
            }

            Logger.info("Looking for function arguments...");

            start = tag.indexOf("#FUNC");

            if (start != -1) {
                int start2 = tag.indexOf("{", start) + 1;
                int end = tag.indexOf("}", start2);

                if (end == -1) {
                    UnsupportedSyntaxError ex = new UnsupportedSyntaxError();
                    Logger.error("Unsupported syntax.", ex);
                }

                result.put("func", tag.substring(start2, end));

                Logger.info("Function arguments found: " + result.get("func"));
            }

            Logger.info("Parsing completed.");

            return result;
        }catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
        return null;
    }

}
