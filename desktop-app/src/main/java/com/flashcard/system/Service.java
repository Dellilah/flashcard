package com.flashcard.system;

import com.flashcard.dto.LoginDTO;
import com.flashcard.dto.WordDTO;
import com.google.gson.*;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 26/04/2013
 * Time: 11:25
 */

@Component
@Scope("singleton")
public class Service {
    private static Service instance = null;

    public enum Language {
        en, pl
    }

    public boolean signIn(String email, String password) throws Exception {

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

    public void deleteWord(Integer id) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words/" + id + ".json?api_token=" + Settings.getToken();
            Content s = Request.Delete(uri).execute().returnContent();
            System.out.println(s.asString());
            System.out.println(s.asString());
        } catch (IOException e) {
            throw new Exception("Cannot delete word");
        }
    }

    public void addNewWord(String englishWord, String polishWord, String remoteImageUrl) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words.json?api_token=" + Settings.getToken();
            Content s = Request.Post(uri)
                    .bodyForm(
                            Form.form()
                                    .add("in_english", englishWord)
                                    .add("in_polish", polishWord)
                                    .add("remote_image_url", remoteImageUrl)
                                    .build()
                            , Charset.defaultCharset()).execute().returnContent();
        } catch (IOException e) {
            System.out.println("IT'S HEEEEERE");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void editWord(Integer id, String inEnglish, String inPolish, String remoteImageUrl) throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words/" + id + ".json?api_token=" + Settings.getToken();
            Content s = Request.Put(uri)
                    .bodyForm(
                            Form.form()
                                    .add("in_english", inEnglish)
                                    .add("in_polish", inPolish)
                                    .add("remote_image_url", remoteImageUrl)
                                    .build()
                            , Charset.defaultCharset()).execute().returnContent();
            //System.out.println(s.asString());
        } catch (IOException e) {
            throw new Exception("Cannot edit word");
        }
    }

    public WordDTO getWord(Integer id) throws Exception {
        try {
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
        } catch (IOException e) {
            throw new Exception("Cannot get word");
        }
    }

    public List<String> getTranslation(Language fromLanguage, String word) throws Exception {
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

    public List<WordDTO> wordsIndex() throws Exception {
        try {
            String uri = Settings.getHost() + "/api/words.json?api_token=" + Settings.getToken();
            Content s = Request.Get(uri).execute().returnContent();
            Gson gson = new Gson();
            WordDTO[] result = gson.fromJson(s.asString(), WordDTO[].class);
            return Arrays.asList(result);
        } catch (IOException e) {
            throw new Exception("Cannot get words");
        }
    }

    public List<String> getImages(String keyword, int limit) throws Exception {
        try {
            URIBuilder uriBuilder = new URIBuilder("https://ajax.googleapis.com/ajax/services/search/images")
                    .addParameter("v", "1.0")
                    .addParameter("q", keyword)
                    .addParameter("key", "ABQIAAAAMDidA1PAO0alsihAElsy3xTLCrE5uk8Ud_JrDKiWLKYeT0PD8xQ9hbFvmXJ2enaXdFRHJflbRAe36A")
                    .addParameter("userip", "222.222.222.10");
//            String uri = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + keyword + "&key=ABQIAAAAMDidA1PAO0alsihAElsy3xTLCrE5uk8Ud_JrDKiWLKYeT0PD8xQ9hbFvmXJ2enaXdFRHJflbRAe36A&userip=222.222.222.10";
            Content s = Request.Get(uriBuilder.toString()).execute().returnContent();
            System.out.println(s.asString());

            List<String> result = new ArrayList<>(limit);
            JsonElement jelement = new JsonParser().parse(s.asString());
            JsonObject  jobject = jelement.getAsJsonObject();
            jobject = jobject.getAsJsonObject("responseData");
            JsonArray jarray = jobject.getAsJsonArray("results");
            for (int i = 0; i <= limit && i < jarray.size(); i++) {
                jobject = jarray.get(i).getAsJsonObject();
                result.add(jobject.get("url").getAsString());
            }

            return result;
        } catch (Exception e) {
            throw new Exception("Cannot get image");
        }
    }

    public static Service getInstance() {
        if (instance == null)
            instance = new Service();
        return instance;
    }
}
