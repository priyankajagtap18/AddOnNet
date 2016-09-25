package com.addonnet.sync;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public interface ParseListener {
    public void onParseSuccess(int taskId, String str, ArrayList<?> arrResult);

    public void onParseFailure(String str, Throwable error);
}
