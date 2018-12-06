package br.edu.ufrn.brenov.luanews.controller.database.news;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import br.edu.ufrn.brenov.luanews.model.News;

public class ReadLaterDatabase {

    private final static String FILE_NAME = "luanews_readlater_database.json";
    private final static String NEWS_LIST = "news_list";

    private final static String LINK = "link";
    private final static String TITLE = "title";
    private final static String AUTHOR = "author";
    private final static String DESCRIPTION = "description";
    private final static String DATE = "date";

    public final static int NEWS_ADDED = 0;
    public final static int NEWS_REMOVED = 1;
    public final static int NEWS_NOT_REMOVED = 2;
    public final static int INVALID_NEWS = -1;

    private static void createDatabase(Context context) throws JSONException, IOException {
        // Create object
        JSONObject json = new JSONObject();
        json.put(NEWS_LIST, new JSONArray());
        // Write file
        OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME,
                Context.MODE_PRIVATE));
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.write(json.toString());
            pw.flush();
        }
    }

    public static List<News> getNews(Context context) throws JSONException, IOException {
        // Open file
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(context.openFileInput(FILE_NAME));
        } catch (FileNotFoundException ex) {
            createDatabase(context);
            return getNews(context);
        }
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
        // Check if the news list was registered
        if (json == null) {
            return null;
        } else {
            List<News> lists = new ArrayList<>();
            JSONArray array = json.getJSONArray(NEWS_LIST);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jon = array.getJSONObject(i);
                News n = new News(jon.getString(LINK), jon.getString(TITLE), jon.getString(AUTHOR),
                        jon.getString(DESCRIPTION), jon.getString(DATE));
                lists.add(n);
            }
            return lists;
        }
    }

    public static boolean contains(News news, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (news == null) {
            return false;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(context.openFileInput(FILE_NAME));
        } catch (FileNotFoundException ex) {
            createDatabase(context);
            return contains(news, context);
        }
        try(Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();
            // Get next line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }
            json = new JSONObject(sb.toString());
        }
        JSONArray array = json.getJSONArray(NEWS_LIST);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jon = array.getJSONObject(i);
            if (news.getLink().equals(jon.getString(LINK))) {
                return true;
            }
        }
        return false;
    }

    public static int add(News news, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (news == null) {
            return INVALID_NEWS;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(context.openFileInput(FILE_NAME));
        } catch (FileNotFoundException ex) {
            createDatabase(context);
            return add(news, context);
        }
        try(Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();
            // Get next line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }
            json = new JSONObject(sb.toString());
        }
        // Add new object
        JSONObject j = new JSONObject();
        j.put(LINK, news.getLink());
        j.put(TITLE, news.getTitle());
        j.put(AUTHOR, news.getAuthor());
        j.put(DESCRIPTION, news.getDescription());
        j.put(DATE, news.getPublishedDate());
        json.getJSONArray(NEWS_LIST).put(j);
        // Write file
        OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME,
                Context.MODE_PRIVATE));
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.write(json.toString());
            pw.flush();
        }
        return NEWS_ADDED;
    }

    public static int remove(News news, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (news == null) {
            return INVALID_NEWS;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(context.openFileInput(FILE_NAME));
        } catch (FileNotFoundException ex) {
            createDatabase(context);
            return remove(news, context);
        }
        try(Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();
            // Get next line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }
            json = new JSONObject(sb.toString());
        }
        // Overwrite object
        JSONArray array = json.getJSONArray(NEWS_LIST);
        int index = -1;
        for (int i = 0; i < array.length(); i++) {
            JSONObject jo = array.getJSONObject(i);
            if (jo.getString(LINK).equals(news.getLink())) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            array.remove(index);
            // Write file
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME,
                    Context.MODE_PRIVATE));
            try (PrintWriter pw = new PrintWriter(out)) {
                pw.write(json.toString());
                pw.flush();
            }
            return NEWS_REMOVED;
        }
        return NEWS_NOT_REMOVED;
    }
}
