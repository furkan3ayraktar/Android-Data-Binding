package com.furkanbayraktar.databinding.model;

import com.furkanbayraktar.databinding.exception.POJOParseException;
import com.furkanbayraktar.databinding.logging.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class BasePOJO implements Serializable {

    public Object get(String fieldName){
        try {
            Logger.info("Get field: " + fieldName);

            Method method;
            Object value = null;

            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            try {
                method = ((Object) this).getClass().getDeclaredMethod(methodName);
                method.setAccessible(true);
                value = method.invoke(this);
            } catch (Exception e) {
                Logger.info("Method not found with name: " + methodName);
            }

            if (value == null) {
                methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                try {
                    method = ((Object) this).getClass().getDeclaredMethod(methodName);
                    method.setAccessible(true);
                    value = method.invoke(this);
                } catch (Exception e) {
                    Logger.info("Method not found with name: " + methodName);
                }

                if (value == null) {
                    Field field;

                    try {
                        field = ((Object) this).getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        value = field.get(this);
                    } catch (NoSuchFieldException e) {
                        POJOParseException ex = new POJOParseException(e);
                        Logger.warn("Field not found.", ex);
                    } catch (IllegalAccessException e) {
                        POJOParseException ex = new POJOParseException("Field access not authorized.", e);
                        Logger.warn("Field access error.", ex);
                    }
                }
            }
            Logger.info("Field value: " + value);

            return value;
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
        return null;
    }

    public void set(String fieldName, Object value){
        try {
            Logger.info("Set field: " + fieldName + " to value: " + value);

            Method method;

            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            try {
                method = ((Object) this).getClass().getDeclaredMethod(methodName, Object.class);
                method.setAccessible(true);
                method.invoke(this, value);

                Logger.info("Field value set to: " + value);
            } catch (Exception e) {
                Logger.info("Method not found with name: " + methodName);

                Field field;

                try {
                    field = ((Object) this).getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(this, value);

                    Logger.info("Field value set to: " + value);
                } catch (NoSuchFieldException ee) {
                    POJOParseException ex = new POJOParseException(ee);
                    Logger.warn("Field not found.", ex);
                } catch (IllegalAccessException ee) {
                    POJOParseException ex = new POJOParseException("Field access not authorized.", ee);
                    Logger.warn("Field access error.", ex);
                }
            }

        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
    }
}
