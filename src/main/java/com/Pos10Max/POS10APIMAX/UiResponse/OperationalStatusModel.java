package com.Pos10Max.POS10APIMAX.UiResponse;

public class OperationalStatusModel {
    private String operationName;
    private String operationResult;
    public String getOperationType() {
        return operationName;
    }
    public void setOperationType(String operationName) {
        this.operationName = operationName;
    }
    public String getOperationResult() {
        return operationResult;
    }
    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }
}
