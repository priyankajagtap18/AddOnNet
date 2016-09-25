/*
 * Name: $RCSfile: StringUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 1:54:00 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.addonnet.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtility class
 * 
 * @author Lemon
 * 
 */
public final class StringUtility {
	/**
	 * Check Edit Text input string
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEmpty(EditText editText) {
		if (editText == null
				|| editText.getEditableText() == null
				|| editText.getEditableText().toString().trim()
						.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public static String replaceImagePathInHtml(String htmlFileName,
			String imagePath, Context context) {
		String result = "";
		InputStream is;

		ArrayList<String> listUrlImage = new ArrayList<String>();
		try {
			is = context.getResources().getAssets().open(htmlFileName);

			String textfile = convertStreamToString(is);

			Pattern titleFinder = Pattern.compile(
					"<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
					Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher regexMatcher = titleFinder.matcher(textfile);
			while (regexMatcher.find()) {
				Log.i("==== Image Src", regexMatcher.group(1));
				listUrlImage.add(regexMatcher.group(1));
			}
			for (String string : listUrlImage) {
				String fileName = string.substring(string.lastIndexOf("/") + 1,
						string.length());
				Log.i("Lemon", "File name :" + fileName);
				textfile = textfile.replace(string, "file:///android_asset/"
						+ imagePath + fileName);

			}
			// Log.i("HTML CONTENT ", textfile);
			result = textfile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static String replaceImagePathInHtml2(String htmlInput,
			String imagePath, Context context) {
		String result = "";

		ArrayList<String> listUrlImage = new ArrayList<String>();
		String textfile = htmlInput.replace("\"\"", "\"");
		Pattern titleFinder = Pattern.compile(
				"<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
				Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher = titleFinder.matcher(textfile);
		while (regexMatcher.find()) {
			Log.i("==== Image Src", regexMatcher.group(1));
			listUrlImage.add(regexMatcher.group(1));
		}
		for (String string : listUrlImage) {
			String fileName = string.substring(string.lastIndexOf("/") + 1,
					string.length());
			Log.i("Lemon", "File name :" + fileName);
			textfile = textfile.replace(string, "file:///android_asset/"
					+ imagePath + fileName);
			// Log.d("StringUtility", textfile);
		}
		// Log.i("HTML CONTENT ", textfile);
		result = textfile;
		return result;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		Writer writer = new StringWriter();

		char[] buffer = new char[2048];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		String text = writer.toString();
		return text;
	}

	/**
	 * Check input string
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEmpty(String editText) {
		if (editText == null || editText.trim().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public static String getSubString(String input, int maxLength) {
		String temp = input;
		if (input.length() < maxLength)
			return temp;
		else
			return input.substring(0, maxLength - 1) + "...";
	}

	/**
	 * Merge all elements of a string array into a string
	 * 
	 * @param strings
	 * @param separator
	 * @return
	 */
	public static String join(String[] strings, String separator) {
		StringBuffer sb = new StringBuffer();
		int max = strings.length;
		for (int i = 0; i < max; i++) {
			if (i != 0)
				sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	/**
	 * Convert current date time to string
	 * 
	 * @return
	 */
	public static String convertNowToFullDateString() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Calendar calendar =
		// Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// return dateformat.format(calendar.getTime());
		return dateformat.format(new Date());
	}

	public static String convertNowToDateString(String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		// dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Calendar calendar =
		// Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// return dateformat.format(calendar.getTime());
		return dateformat.format(new Date());
	}

	/**
	 * Initial sync date string
	 * 
	 * @return
	 */
	public static String initDateString() {
		return "1900-01-01 09:00:00";
	}

	/**
	 * Convert a string divided by ";" to multiple xmpp users
	 * 
	 * @param userString
	 * @return
	 */
	public static String[] convertStringToXmppUsers(String userString) {
		return userString.split(";");
	}

	/**
	 * get Unique Random String
	 * 
	 * @return
	 */

	public static String getUniqueRandomString() {

		// return String.valueOf(System.currentTimeMillis());
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}

	// public static String getUniqueRandomString() {
	//
	// return UUID.randomUUID().toString();
	//
	// }
	public static String getDateString(String dateFormat, String longVvalue) {
		String outputStr = "";
		SimpleDateFormat outputForm = new SimpleDateFormat(dateFormat);

		Date startDate;
		startDate = new Date(Long.parseLong(longVvalue));
		outputStr = outputForm.format(startDate);
		return outputStr;

	}

	public static SpannableString formatProductPrice(Context context,
			String currency, String price, int integerNumberStyle,
			int fractionalNumberStyle) {

		try {
			int priceInt = (int)Float.parseFloat(price);
			
			String productPrice = currency
					+ String.format("%.2f", Float.parseFloat(price));
			int dotIndex = String.valueOf(priceInt).length() + 1;

			SpannableString textPrice = new SpannableString(productPrice);
			textPrice.setSpan(new TextAppearanceSpan(context,
					fractionalNumberStyle), 0, 1,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			textPrice.setSpan(new TextAppearanceSpan(context,
					integerNumberStyle), 1, dotIndex,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			textPrice.setSpan(new TextAppearanceSpan(context,
					fractionalNumberStyle), dotIndex, textPrice.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			return textPrice;
		} catch (Exception e) {
			e.printStackTrace();
			return new SpannableString("");
		}
	}

	public static String[] getProductExtraOptions(String option) {
		try {
			String[] options = option.split(" \\| ");

			for (String s : options) {
				Log.i("EXTRA_OPTIONS", s);
			}
			return options;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[0];
		}
	}
}
