package com.example.givinghandproject.dto;

public class ErrorDTO {
    private String message;
    private String field;

    public ErrorDTO(){}
    public ErrorDTO(String field,String message  )
    {
        setMessage(message);
        setField(field);
    }

    public void setMessage(String message){this.message = message;}
    public String getMessage(){return message;}

    public void setField(String field){this.field =field;}
    public String getField(){return field;}
}
