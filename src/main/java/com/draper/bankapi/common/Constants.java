package com.draper.bankapi.common;

public abstract class Constants {
    private Constants() throws Exception {
        String message = String.format(MSG_DO_NOT_INSTANTIATE, Constants.class.getName());
        throw new Exception(message);
    }

    public static final String MSG_MISSING_JSON_VALUE = "%s must be provided";
    public static final String MSG_DO_NOT_INSTANTIATE = "%s should not be instantiated";
}
