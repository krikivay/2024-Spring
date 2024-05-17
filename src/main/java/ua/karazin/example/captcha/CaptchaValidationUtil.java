package ua.karazin.example.captcha;


import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

@Component
public class CaptchaValidationUtil {
    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6Ld2GFwaAAAAAPsLZ8PQsna8b1mLOtelB3FQ7HI0";

    public boolean isCaptchaValid(String response) throws CaptchaException {
        if (response == null || response.isEmpty()) {
            return false;
        }

        try {
            URL obj = new URL(URL);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            String postParams = "secret=" + SECRET_KEY + "&response="
                    + response;

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder stringBuffer = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(stringBuffer.toString());
            in.close();
            return json.getBoolean("success");
        } catch (Exception e) {
            throw new CaptchaException("Captcha validation failed");
        }
    }
}
