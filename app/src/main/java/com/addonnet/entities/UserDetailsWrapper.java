
package com.addonnet.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDetailsWrapper {

    @SerializedName("UserDetails")
    @Expose
    private ArrayList<UserDetail> userDetails = new ArrayList<UserDetail>();

    /**
     * 
     * @return
     *     The userDetails
     */
    public ArrayList<UserDetail> getUserDetails() {
        return userDetails;
    }

    /**
     * 
     * @param userDetails
     *     The UserDetails
     */
    public void setUserDetails(ArrayList<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }

}
