package com.proyecto.inventario.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@ToString
@Data
public class Event<T> {
    private String id;
    private Date date;
    private EventType type;
    private T data;
    
    public Event() {
        // Constructor predeterminado vacío
    }
    
    @JsonCreator
    public Event(@JsonProperty("id") String id, 
                 @JsonProperty("date") Date date, 
                 @JsonProperty("type") EventType type, 
                 @JsonProperty("data") T data) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.data = data;
    }
    

}
