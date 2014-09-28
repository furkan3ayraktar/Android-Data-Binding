package com.furkanbayraktar.databinding.common.resolver;

import android.view.View;
import android.widget.CompoundButton;
import com.furkanbayraktar.databinding.exception.FunctionException;
import com.furkanbayraktar.databinding.exception.ResolverException;
import com.furkanbayraktar.databinding.logging.Logger;
import com.furkanbayraktar.databinding.model.BasePOJO;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 *
 */
public class FunctionResolver {

    private static FunctionResolver instance;

    private FunctionResolver(){
        Logger.info("FunctionResolver object created.");
    }

    public static FunctionResolver getInstance(){
        if(instance == null){
            instance = new FunctionResolver();
        }
        return instance;
    }

    public void resolve(View view, String tag, BasePOJO object, final Object actionObject) {

        if (actionObject == null) {
            ResolverException ex = new ResolverException("Action object not defined!");
            Logger.error("Action object not defined.", ex);
        } else {
            try {
                Logger.info("Searching for function name...");

                Object[] args = null;
                Class[] classes = null;

                int start = tag.indexOf("#methodName");
                start = tag.indexOf(":", start);
                int end = tag.length();

                if (tag.contains("#args")) {
                    end = tag.indexOf("#args");

                    Logger.info("Searching for function arguments...");

                    String argsString = tag.substring(tag.indexOf(":", end) + 1).trim();
                    String[] argKeys = argsString.split(",");
                    if(view instanceof CompoundButton) {
                        classes = new Class[argKeys.length+1];
                        args = new Object[argKeys.length+1];
                        classes[0] = Boolean.class;
                    }else{
                        classes = new Class[argKeys.length];
                        args = new Object[argKeys.length];
                    }


                    for (int i = 0; i < argKeys.length; i++) {
                        Object temp = PathResolver.getInstance().resolve(argKeys[i], object);
                        if(view instanceof CompoundButton){
                            args[i+1] = temp;
                        }else {
                            args[i] = temp;
                        }
                        if (temp == null) {
                            if(view instanceof CompoundButton){
                                classes[i+1] = Object.class;
                            }else {
                                classes[i] = Object.class;
                            }
                        } else {
                            if(view instanceof CompoundButton) {
                                classes[i+1] = temp.getClass();
                            }else{
                                classes[i] = temp.getClass();
                            }
                        }
                    }

                    Logger.info("Argument search completed.");
                }

                Logger.info("Function name search completed.");

                final String methodName = tag.substring(start + 1, end).trim();

                final Class[] finalClasses = classes;
                final Object[] finalArgs;
                if(args == null) {
                    finalArgs = new Object[1];
                }else{
                    finalArgs = args;
                }

                Logger.info("Setting on click listener for view...");

                if(view instanceof CompoundButton){

                    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            finalArgs[0] = b;
                            try {
                                if (finalClasses != null) {
                                    actionObject.getClass().getDeclaredMethod(methodName, finalClasses).invoke(actionObject, finalArgs);
                                } else {
                                    actionObject.getClass().getDeclaredMethod(methodName, Boolean.class).invoke(actionObject, b);
                                }
                            } catch (NoSuchMethodException e) {
                                FunctionException ex = new FunctionException("Function not found with name: " + methodName, e);
                                Logger.error("Function not found.", ex);
                            } catch (InvocationTargetException e) {
                                FunctionException ex = new FunctionException("Invocation error for method: " + methodName, e);
                                Logger.error("Invocation error.", ex);
                            } catch (IllegalAccessException e) {
                                FunctionException ex = new FunctionException("Illegal access to method: " + methodName, e);
                                Logger.error("Illegal Access.", ex);
                            } catch (Exception e) {
                                FunctionException ex = new FunctionException("Unexpected error during call for method: " + methodName, e);
                                Logger.error("Unexpected Error.", ex);
                            }
                        }
                    });

                }else {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (finalClasses != null) {
                                    actionObject.getClass().getDeclaredMethod(methodName, finalClasses).invoke(actionObject, finalArgs);
                                } else {
                                    actionObject.getClass().getDeclaredMethod(methodName).invoke(actionObject);
                                }
                            } catch (NoSuchMethodException e) {
                                FunctionException ex = new FunctionException("Function not found with name: " + methodName, e);
                                Logger.error("Function not found.", ex);
                            } catch (InvocationTargetException e) {
                                FunctionException ex = new FunctionException("Invocation error for method: " + methodName, e);
                                Logger.error("Invocation error.", ex);
                            } catch (IllegalAccessException e) {
                                FunctionException ex = new FunctionException("Illegal access to method: " + methodName, e);
                                Logger.error("Illegal Access.", ex);
                            } catch (Exception e) {
                                FunctionException ex = new FunctionException("Unexpected error during call for method: " + methodName, e);
                                Logger.error("Unexpected Error.", ex);
                            }
                        }
                    });
                }

                Logger.info("On click listener set.");

            } catch (Exception ex) {
                Logger.error("Unexpected error.", ex);
            }
        }
    }
}
