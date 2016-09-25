package com.addonnet.sync;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public interface SyncListener
{

    void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult);

    void onSyncFailure(int taskId, String message);

    void onSyncProgressUpdate(String message);

}

