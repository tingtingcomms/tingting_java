package api.tingting.im;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TingTingAPI {
	public static final String API_URL = "https://v1.tingting.im/api";
	protected String mApiKey;
	
	public TingTingAPI(String apikey) {
		this.mApiKey = apikey;
	}
	public String sendSMS(String to, String content, String sender) throws IOException {
		String json = "{\"to\": \"" + to + "\", \"content\": \"" + EncodeNonAsciiCharacters(content) + "\", \"sender\":\"" + sender + "\"}";
		URL url = new URL(API_URL + "/sms");
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty ("apikey", this.mApiKey);
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(json);
		wr.flush();
		wr.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine = "";
		StringBuffer buffer = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			buffer.append(inputLine);
		}
		in.close();
		return buffer.toString();
	}
	
	private String EncodeNonAsciiCharacters(String value) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
        	char c = value.charAt(i);
        	int unit = (int)c;
        	if (unit > 127) {
        		String hex = String.format("%04x", (int)unit);
	        	String encodedValue = "\\u" + hex;
	        	sb.append(encodedValue);
        	}
        	else {
        		sb.append(c);
        	}
        }
        return sb.toString();
    }

}
