package com.production.ehayvanbackendapi.DTO;
public class MedTypeDTO {
    private Integer medTypeID;
    private String medType;
    public MedTypeDTO(){

    }
    public MedTypeDTO(Integer medTypeID, String medTypeName) {
        this.medTypeID = medTypeID;
        this.medType = medTypeName;
    }
    public Integer getMedTypeID() {
        return medTypeID;
    }
    public void setMedTypeID(int id){
        this.medTypeID = id;
    }
    public String getMedTypeName() {
        return medType;
    }

    public void setMedTypeName(String medTypeName) {
        this.medType = medTypeName;
    }

}