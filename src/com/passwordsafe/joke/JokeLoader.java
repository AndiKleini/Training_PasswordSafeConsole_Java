package com.passwordsafe.joke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JokeLoader {

    public static final String HTTPS_API_CHUCKNORRIS_IO_JOKES_RANDOM = "https://api.chucknorris.io/jokes/random";

    public static String requestJoke() throws IOException {
        StringBuffer content = getRandomJokeResponse();
        return extractJoke(content);
    }

    private static String extractJoke(StringBuffer content) {
        String regex = ".*\\\"value\\\":\\\"(.*)\\\"\\}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher =  pattern.matcher(content);
        String joke = null;
        if(matcher.find()) {
            joke = matcher.group(1);
        } else {
            joke = "Unable to extract joke.";
        }
        return joke;
    }

    private static StringBuffer getRandomJokeResponse() throws IOException {
        URL url = new URL(HTTPS_API_CHUCKNORRIS_IO_JOKES_RANDOM);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content;
    }
}
