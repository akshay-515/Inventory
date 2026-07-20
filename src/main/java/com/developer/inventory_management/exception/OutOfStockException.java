package com.developer.inventory_management.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException (String message){
        super(message);
    }
}
