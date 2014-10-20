package com.setecs.mobile.safe.apps.shared;

import org.json.JSONException;
import org.json.JSONObject;


public class AccountBalanceParser {

	public static String parse(String response) {
		String output = response;

		try {
			JSONObject object = new JSONObject(response);

			String accountNo = "Account No: " + object.getJSONObject("accountId").getString("accountNo") + "\n";
			String accountType = "Account Type: " + object.getJSONObject("accountId").getString("type") + "\n";
			String balance = "Balance: " + object.getString("balanceAvailable") + "\n";
			String currency = "Currency: " + object.getString("currency") + "\n";

			output = accountType + accountNo + balance + currency;
		}
		catch (JSONException e) {
			// ignore
		}

		return output;

	}

}
