package com.addonnet.constants;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public interface AppUrls {


    String sBaseURL = "http://www.webdreamworksindia.in/addonsystem/";

    String spAuthentication = sBaseURL + "UserAuthentication.aspx?";
    String spRegistration = sBaseURL + "UserRegistration.aspx?UserId=0&UpdateType=&StatusId=1";
    String spCategories = sBaseURL + "GetCategoryLst.aspx?CategoryId=0&StatusId=0";
    String spProducts = sBaseURL + "ProductLst.aspx?ProductId=0&StatusId=0";
    String spEnquiry= sBaseURL + "EnquiryAddUpd.aspx?StatusId=1&UpdateType=Add&EnquiryId=0";


}