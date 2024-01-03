package com.production.ehayvanbackendapi.DTO;

import java.util.List;

public class UserTypeDTO {
    private Integer userTypeID;
    private String type;
    public UserTypeDTO(){

    }
    public UserTypeDTO(Integer userTypeID, String type, List<CustomerDTO> users) {
        this.userTypeID = userTypeID;
        this.type = type;
    }

    public Integer getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(Integer userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
