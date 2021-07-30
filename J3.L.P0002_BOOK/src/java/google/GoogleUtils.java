/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author ADMIN
 */
public class GoogleUtils {

    public static String getToken(final String code) throws IOException {
        String response = Request.Post(SocialConstant.GOOGLE_LINK_GET_TOKEN).
                bodyForm(Form.form().add("client_id", SocialConstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", SocialConstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", SocialConstant.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", SocialConstant.GOOGLE_GRANT_TYPE).build()).execute().returnContent().asString();
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        String accessToken = json.get("access_token").toString().replaceAll("\"", "");
        System.out.println("\nToken:\n" + accessToken);
        return accessToken;
    }

    public static GooglePOJO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = SocialConstant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        System.out.println("\nresponse:\n" + response);
        GooglePOJO googlePOJO = new Gson().fromJson(response, GooglePOJO.class);
        System.out.println(googlePOJO);
        return googlePOJO;
    }
}
