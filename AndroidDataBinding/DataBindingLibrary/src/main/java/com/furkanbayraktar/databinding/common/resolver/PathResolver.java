package com.furkanbayraktar.databinding.common.resolver;

import com.furkanbayraktar.databinding.exception.POJOParseException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class PathResolver {

    private static PathResolver instance;

    private PathResolver(){
        Logger.info("PathResolver object created.");
    }

    public static PathResolver getInstance(){
        if(instance == null){
            instance = new PathResolver();
        }
        return instance;
    }

    public Object resolve(String tag, BasePOJO object){

        try {
            Logger.info("Resolving tag: " + tag + "...");

            Object result = null;
            tag = tag.trim();

            if (tag.contains(":")) {
                String path[] = tag.split(":");
                Object temp = object;
                for (int j = 0; j < path.length - 1; j++) {
                    String argName = null;
                    boolean isArray = false;
                    int index = -1;
                    if (path[j].contains("[")) {
                        try {
                            int indexOfOpenBracket = path[j].indexOf('[');
                            int indexOfCloseBracket = path[j].indexOf(']');
                            argName = path[j].substring(0, indexOfOpenBracket);
                            index = Integer.parseInt(path[j].substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                        } catch (Exception e) {
                            Logger.error("Parse error. Index must be an integer.",e);
                            e.printStackTrace();
                        }
                        isArray = true;
                    } else {
                        argName = path[j];
                        isArray = false;
                    }

                    Method m = null;

                    if (argName != null) {
                        try {
                            m = temp.getClass().getSuperclass().getDeclaredMethod("get", String.class);
                        } catch (NoSuchMethodException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (Exception e) {
                            POJOParseException ex = new POJOParseException("Unexpected error", e);
                            Logger.error("Unexpected error.", ex);
                        }
                    }
                    if (m != null) {
                        try {
                            if (!isArray) {
                                temp = m.invoke(temp, argName);
                            } else {
                                Object obj = m.invoke(temp, argName);
                                if (obj instanceof ArrayList) {
                                    temp = ((ArrayList<Object>) m.invoke(temp, argName)).get(index);
                                } else {
                                    temp = ((Object[]) m.invoke(temp, argName))[index];
                                }
                            }
                        } catch (IllegalAccessException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (InvocationTargetException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (Exception e) {
                            POJOParseException ex = new POJOParseException("Unexpected error.", e);
                            Logger.error("Unexpected error.", ex);
                        }
                    }
                }
                if (temp != object) {
                    String lastPath = path[path.length - 1];
                    if (lastPath.contains("[")) {
                        try {
                            int indexOfOpenBracket = lastPath.indexOf('[');
                            int indexOfCloseBracket = lastPath.indexOf(']');
                            String arrayName = lastPath.substring(0, indexOfOpenBracket);
                            int index = Integer.parseInt(lastPath.substring(indexOfOpenBracket + 1, indexOfCloseBracket));
                            try {
                                Object obj = temp.getClass().getSuperclass().getDeclaredMethod("get", String.class).invoke(temp, arrayName);
                                if (obj instanceof ArrayList) {
                                    result = ((ArrayList<Object>) obj).get(index);
                                } else {
                                    result = ((Object[]) obj)[index];
                                }
                            } catch (NoSuchMethodException e) {
                                POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                                Logger.error("BasePOJO error.", ex);
                            } catch (InvocationTargetException e) {
                                POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                                Logger.error("BasePOJO error.", ex);
                            } catch (IllegalAccessException e) {
                                POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                                Logger.error("BasePOJO error.", ex);
                            } catch (Exception e) {
                                POJOParseException ex = new POJOParseException("Unexpected error.", e);
                                Logger.error("Unexpected error.", ex);
                            }
                        } catch (Exception e) {
                            POJOParseException ex = new POJOParseException("Unexpected error.", e);
                            Logger.error("Unexpected error.", ex);
                        }
                    } else {
                        try {
                            result = temp.getClass().getSuperclass().getDeclaredMethod("get", String.class).invoke(temp, lastPath);
                        } catch (NoSuchMethodException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (InvocationTargetException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (IllegalAccessException e) {
                            POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                            Logger.error("BasePOJO error.", ex);
                        } catch (Exception e) {
                            POJOParseException ex = new POJOParseException("Unexpected error.", e);
                            Logger.error("Unexpected error.", ex);
                        }
                    }
                }
            } else {
                if (tag.contains("[")) {
                    int indexOfOpenBracket = tag.indexOf('[');
                    int indexOfCloseBracket = tag.indexOf(']');
                    String arrayName = tag.substring(0, indexOfOpenBracket);
                    int index = Integer.parseInt(tag.substring(indexOfOpenBracket + 1, indexOfCloseBracket));

                    try {
                        Object obj = ((Object)object).getClass().getSuperclass().getDeclaredMethod("get", String.class).invoke(object, arrayName);
                        if (obj instanceof ArrayList) {
                            result = ((ArrayList<Object>) obj).get(index);
                        } else {
                            result = ((Object[]) obj)[index];
                        }
                    } catch (NoSuchMethodException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (InvocationTargetException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (IllegalAccessException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (Exception e) {
                        POJOParseException ex = new POJOParseException("Unexpected error.", e);
                        Logger.error("Unexpected error.", ex);
                    }
                } else {
                    try {
                        result = ((Object)object).getClass().getSuperclass().getDeclaredMethod("get", String.class).invoke(object, tag);
                    } catch (NoSuchMethodException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (InvocationTargetException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (IllegalAccessException e) {
                        POJOParseException ex = new POJOParseException("Object does not have proper get method (extend BasePOJO!)", e);
                        Logger.error("BasePOJO error.", ex);
                    } catch (Exception e) {
                        POJOParseException ex = new POJOParseException("Unexpected error.", e);
                        Logger.error("Unexpected error.", ex);
                    }
                }
            }

            Logger.info("Tag resolved to value: " + result);

            return result;
        } catch (Exception ex){
            Logger.error("Unexpected error.", ex);
        }
        return null;
    }
}
