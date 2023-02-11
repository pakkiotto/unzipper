package model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class InputModel {
    @JsonProperty("message")
    private String message;

    public InputModel(){

    }

    public InputModel(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
