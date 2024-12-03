package com.example.librarymanagernqc.AbstractClass;

public abstract class HasError {
    private String errorMessage = null;

    /**
     * @return latest error message, if not exist return null
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    protected void setErrorMessage(String newErrorMessage) {
        errorMessage = newErrorMessage;
    }
}
