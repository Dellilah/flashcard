package com.flashcard.system;

import com.flashcard.dto.LoginDTO;
import com.flashcard.dto.WordDTO;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 26/04/2013
 * Time: 11:25
 */
public class Service {
    private static Service instance = null;

    public static Service getInstance() {
        if (instance == null)
            instance = new Service();
        return instance;
    }

    public enum Language {
        en, pl
    }

    public static boolean signIn(String email, String password) throws Exception {

        try {
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
                loginDTO = new LoginDTO();
                loginDTO.setApi_token("1");
            }
            Settings.setToken(loginDTO.getApi_token());
            Settings.setLogin(email);
            Settings.setPassword(password);
            Settings.writeSettings();
            return true;
        } catch (UnknownHostException e) {
            throw new Exception("Cannot connect with server: " + e.getMessage());
        }
    }

    public static void deleteWord(Integer id) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words/" + id + ".json?api_token=" + Settings.getToken();
            Content s = Request.Delete(uri).execute().returnContent();
            System.out.println(s.asString());
            System.out.println(s.asString());
        } catch (IOException e) {
            throw new Exception("Cannot delete word");
        }
    }

    public static void addNewWord(String englishWord, String polishWord) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words.json?api_token=" + Settings.getToken();
            Content s = Request.Post(uri)
                    .bodyForm(
                            Form.form()
                                    .add("in_english", englishWord)
                                    .add("in_polish", polishWord)
                                    .build()
                            , Charset.defaultCharset()).execute().returnContent();
        } catch (IOException e) {
            System.out.println("IT'S HEEEEERE");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //nie wiem, czy ta metoda jest dobrze napisana :/
    public static void editWord(Integer id, String inEnglish, String inPolish) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words/" + id + ".json?api_token=" + Settings.getToken();
            Content s = Request.Put(uri)
                    .bodyForm(
                            Form.form()
                                    .add("in_english", inEnglish)
                                    .add("in_polish", inPolish)
                                    .build()
                            , Charset.defaultCharset()).execute().returnContent();
            //System.out.println(s.asString());
        } catch (IOException e) {
            throw new Exception("Cannot edit word");
        }
    }

    public static WordDTO getWord(Integer id) throws Exception{
        try{
            System.out.println(0);
            String url = Settings.getHost() + "/api/words/" + id + ".json?api_token=" + Settings.getToken();
            System.out.println(1);
            Content s = Request.Get(url).execute().returnContent();
            System.out.println(2);
            Gson gson = new Gson();
            System.out.println(3);
            WordDTO result = gson.fromJson(s.asString(), WordDTO.class);
            System.out.println(4);
            return result;
        } catch (IOException e){
            throw new Exception("Cannot get word");
        }
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

    public static List<WordDTO> wordsIndex() throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words.json?api_token=" + Settings.getToken();
            Content s = Request.Get(uri).execute().returnContent();
            System.out.println(s.asString());
            Gson gson = new Gson();
            System.out.println(s.asString());
            WordDTO[] result = gson.fromJson(s.asString(), WordDTO[].class);
            return Arrays.asList(result);
        } catch (IOException e) {
            throw new Exception("Cannot get words");
        }
    }
}
