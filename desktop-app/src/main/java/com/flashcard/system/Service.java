package com.flashcard.system;

import com.flashcard.fx.App;
import com.flashcard.fx.scene.TranslationScene;
import com.flashcard.ui.LoginDTO;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 11:25
 */
public class Service {
    public static boolean signIn(String email, String password) throws Exception {

        String url = Settings.getHost() + "/api/login.json";
        Content s = Request.Post(url)
                .bodyForm(
                        Form.form()
                                .add("email", email)
                                .add("password", password)
                                .build()
                ).execute().returnContent();
        System.out.println(s.asString());
        Gson gson = new Gson();
        LoginDTO loginDTO = gson.fromJson(s.asString(), LoginDTO.class);
        System.out.println(loginDTO.getApi_token());
        if (loginDTO.getMessage() == null && loginDTO.getApi_token() != null) {
            App.getInstance().setScene(new TranslationScene());
        } else {
            throw new Exception("The supplied credentials could not be regarded as correct (G)");
        }
        Settings.setToken(loginDTO.getApi_token());
        return true;
    }
}
