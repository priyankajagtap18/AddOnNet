package com.addonnet.interfaces;

import android.os.Bundle;

/**
 * Created by PriyankaJ on 10-03-2016.
 * This will provide interface between user custom adapter and UI
 * On click of list item it will return bundle object on UI
 */
public interface AdapterResponseInterface {
    public void getAdapterResponse(Bundle bundle);
}
