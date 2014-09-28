package com.furkanbayraktar.databinding.common.parser;

import com.furkanbayraktar.databinding.common.external.Expression;
import com.furkanbayraktar.databinding.common.resolver.PathResolver;
import com.furkanbayraktar.databinding.exception.ResolverException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.math.BigDecimal;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/18/14.
 * Copyright Valensas A.S.
 */
public class ExpressionEvaluator{

    private static ExpressionEvaluator instance;

    private ExpressionEvaluator(){
        Logger.info("ExpressionEvaluator object created.");
    }

    public static ExpressionEvaluator getInstance(){
        if(instance == null){
            instance = new ExpressionEvaluator();
        }
        return instance;
    }

    public BigDecimal evaluate(String tag, BasePOJO object){

        try {
            Logger.info("Expression evaluation started...");

            int start;
            int end;

            do {
                start = tag.indexOf("(");
                if (start != -1) {
                    end = tag.indexOf(")", start);
                } else {
                    end = -1;
                }

                if (end != -1) {
                    String current = tag.substring(start + 1, end);

                    if (current.contains("!=")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("!"), current.indexOf("=") + 1, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains("==")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("="), current.indexOf("=") + 2, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains("<=")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("<"), current.indexOf("=") + 1, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains(">=")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf(">"), current.indexOf("=") + 1, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains("<")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("<"), current.indexOf("<") + 1, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains(">")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf(">"), current.indexOf(">") + 1, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains("&&")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("&"), current.indexOf("&") + 2, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else if (current.contains("||")) {
                        tag = tag.replace(current, findToBeReplaced(current, current.indexOf("|"), current.indexOf("|") + 2, object));

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    } else {
                        Object value = PathResolver.getInstance().resolve(current, object);

                        if (value != null) {
                            String toReplace = current.replace(current, String.valueOf(value));
                            tag = tag.replace(current, toReplace);
                        }

                        start = tag.indexOf("(", end);
                        if (start != -1) {
                            end = tag.indexOf(")", start);
                        } else {
                            end = -1;
                        }
                    }
                }
            } while (end != -1);

            BigDecimal result = null;

            tag = tag.replace("true", "TRUE");
            tag = tag.replace("false", "FALSE");

            try {
                Expression expression = new Expression(tag);
                result = expression.eval();
            } catch (Exception e) {
                ResolverException ex = new ResolverException("Cannot evaluate expression.", e);
                Logger.error("Cannot evaluate expression.", ex);
            }

            Logger.info("Expression evaluation finished.");
            return result;
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
        return null;
    }

        private String findToBeReplaced(String current, int firstIndex, int secondIndex, BasePOJO object){

        try {
            String key1;
            Object value1;

            key1 = current.substring(0, firstIndex).trim();

            Logger.info("Searching value for key: " + key1);

            value1 = PathResolver.getInstance().resolve(key1, object);

            String key2;
            Object value2;

            key2 = current.substring(secondIndex).trim();

            Logger.info("Searching value for key: " + key2);

            value2 = PathResolver.getInstance().resolve(key2, object);

            if (value1 != null) {
                Logger.info("Value found for key: " + key1);

                current = current.replace(key1, String.valueOf(value1));
            }else{
                Logger.info("Value not found for key: " + key1);
            }

            if (value2 != null) {
                Logger.info("Value found for key: " + key2);

                current = current.replace(key2, String.valueOf(value2));
            }else{
                Logger.info("Value not found for key: " + key2);
            }
        } catch (Exception ex){
            
        }

        return current;
    }
}
