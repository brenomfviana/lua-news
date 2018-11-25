package br.edu.ufrn.brenov.luanews.database.auth;

import android.content.Context;

import org.json.*;
import br.edu.ufrn.brenov.luanews.domain.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Auth {

    private final static String FILE_NAME = "luanews_auth.json";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    public static User getUser(Context context) throws JSONException, IOException {
        // Open file
        InputStreamReader in = new InputStreamReader(context.openFileInput(FILE_NAME));
        // Read file
        JSONObject json;
        try(Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();
            // Get next line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }
            json = new JSONObject(sb.toString());
        }
        // Check if the user was registered
        if (json == null) {
            return null;
        } else {
            return new User(json.getString(USERNAME), json.getString(PASSWORD));
        }
    }

    public static void register(User user, Context context) throws JSONException, IOException {
        // Create JSON
        JSONObject json = new JSONObject();
        json.put(USERNAME, user.getUsername());
        json.put(PASSWORD, user.getPassword());
        // Write file
        OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.write(json.toString());
            pw.flush();
        }
    }
}
