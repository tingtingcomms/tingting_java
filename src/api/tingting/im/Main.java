package api.tingting.im;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		TingTingAPI api = new TingTingAPI("Your api key");
		
		try {
			api.sendSMS("849xxxxxx", "test sms", "your sender");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
