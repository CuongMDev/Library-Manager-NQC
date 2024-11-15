package com.example.librarymanagernqc.AbstractClass;

public abstract class HasError {
    private static String errorMessage = null;

    /**
     * @return latest error message, if not exist return null
     */
    public static String getErrorMessage() {
        return errorMessage;
    }

    protected static void setErrorMessage(String newErrorMessage) {
        errorMessage = newErrorMessage;
    }
}
