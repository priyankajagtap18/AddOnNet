package com.addonnet.entities;

import java.util.ArrayList;

/**
 * Created by PravinK on 28-09-2016.
 */
public class EnquiryWrapper
{
    ArrayList<EnquiryAddUpd> EnquiryAddUpd=new ArrayList<>();

    public ArrayList<com.addonnet.entities.EnquiryAddUpd> getEnquiryAddUpd() {
        return EnquiryAddUpd;
    }

    public void setEnquiryAddUpd(ArrayList<com.addonnet.entities.EnquiryAddUpd> enquiryAddUpd) {
        EnquiryAddUpd = enquiryAddUpd;
    }
}
