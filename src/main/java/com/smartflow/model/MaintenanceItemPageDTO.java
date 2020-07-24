package com.smartflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceItemPageDTO {
Integer Id;
String Name;
String Type;
@JsonProperty("Id")
public Integer getId() {
	return Id;
}
@JsonProperty("Name")
public String getName() {
	return Name;
}
@JsonProperty("Type")
public String getType() {
	return Type;
}
public void setId(Integer id) {
	Id = id;
}
public void setName(String name) {
	Name = name;
}
public void setType(String type) {
	Type = type;
}


}
