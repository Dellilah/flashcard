package com.flashcard.system;

import com.flashcard.dto.LoginDTO;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * User: ghaxx
 * Date: 26/04/2013
 * Time: 11:25
 */
public class Service {
    public enum Language {
        en, pl
    }
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
        if (loginDTO.getMessage() != null || loginDTO.getApi_token() == null) {
            throw new Exception("The supplied credentials could not be regarded as correct (G)");
        }
        Settings.setToken(loginDTO.getApi_token());
        Settings.setLogin(email);
        Settings.setPassword(password);
        Settings.writeSettings();
        return true;
    }

    public static List<String> getTranslation(Language fromLanguage, String word) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words/from_" + fromLanguage.name() + "/" + word + ".json?api_token=" + Settings.getToken();
            Content s = Request.Get(uri).execute().returnContent();
            System.out.println(s.asString());
            Gson gson = new Gson();
            String[] result = gson.fromJson(s.asString(), String[].class);
            return Arrays.asList(result);
        } catch (IOException e) {
            throw new Exception("Cannot translate");
        }

    }

    public static void addNewWord(String englishWord, String polishWord) throws Exception{
        try{
            String uri =Settings.getHost() + "/api/words.json?api_token="+Settings.getToken();
            Content s = Request.Post(uri)
                    .bodyForm(
                            Form.form()
                                    .add("in_english", englishWord)
                                    .add("in_polish", polishWord)
                                    .build()
                    ).execute().returnContent();
        }
        catch (IOException e){
            throw new Exception("Something's wrong, Jim.");
        }
    }
}
