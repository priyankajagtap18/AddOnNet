
package com.addonnet.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegistrationWrapper
{
    @SerializedName("Registration")
    @Expose
    private ArrayList<Registration> registration = new ArrayList<Registration>();

    /**
     * 
     * @return
     *     The registration
     */
    public ArrayList<Registration> getRegistration() {
        return registration;
    }

    /**
     * 
     * @param registration
     *     The Registration
     */
    public void setRegistration(ArrayList<Registration> registration) {
        this.registration = registration;
    }
}
