package com.ph.object.prototype;

import java.io.Serializable;

public class WorkExperience implements Serializable, Cloneable {

    private static final long serialVersionUID = 4405810784875264462L;

    private String workDate;

    private String workCompany;

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    @Override
    public Object clone(){
        Object result = null;
        try {
            result = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
