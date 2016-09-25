package com.addonnet.network;

public interface DownloadListener
	{

		public void onDownloadSuccess(int taskId, String strResponse);

		public void onDownloadFailure(int taskId, String strResponse);

	}
