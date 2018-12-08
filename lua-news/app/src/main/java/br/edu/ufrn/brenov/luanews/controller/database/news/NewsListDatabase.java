package br.edu.ufrn.brenov.luanews.controller.database.news;

import android.content.Context;
import android.util.Log;

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
import br.edu.ufrn.brenov.luanews.model.NewsList;

public class NewsListDatabase {

    public final static int ACTION_ADD = 0;
    public final static int ACTION_UPDATE = 1;

    private final static String FILE_NAME = "luanews_newslist_database.json";
    private final static String NEWS_LIST = "news_list";

    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String NEWS = "news";

    private final static String LINK = "link";
    private final static String TITLE = "title";
    private final static String AUTHOR = "author";
    private final static String DESCRIPTION = "description";
    private final static String DATE = "date";

    public final static int LIST_ADDED = 0;
    public final static int LIST_REMOVED = 1;
    public final static int LIST_NOT_REMOVED = 2;
    public final static int LIST_UPDATED = 3;
    public final static int LIST_NOT_UPDATED = 4;
    public final static int INVALID_LIST = -1;

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

    public static List<NewsList> getNewsLists(Context context) throws JSONException, IOException {
        // Open file
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(context.openFileInput(FILE_NAME));
        } catch (FileNotFoundException ex) {
            createDatabase(context);
            return getNewsLists(context);
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
            List<NewsList> lists = new ArrayList<>();
            JSONArray array = json.getJSONArray(NEWS_LIST);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jonl = array.getJSONObject(i);
                // Get news
                List<News> newslist = new ArrayList<>();
                JSONArray news = jonl.getJSONArray(NEWS);
                for (int j = 0; j < news.length(); j++) {
                    JSONObject jon = news.getJSONObject(j);
                    News n = new News(jon.getString(LINK), jon.getString(TITLE), jon.getString(AUTHOR),
                            jon.getString(DESCRIPTION), jon.getString(DATE));
                    newslist.add(n);
                }
                // Add
                lists.add(new NewsList(jonl.getInt(ID), jonl.getString(NAME), newslist));
            }
            return lists;
        }
    }

    public static int add(String name, List<News> newslist, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (name.equals("")) {
            return INVALID_LIST;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = new InputStreamReader(context.openFileInput(FILE_NAME));
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
        JSONObject jo = new JSONObject();
        jo.put(NAME, name);
        // Get ID
        List<NewsList> nl = getNewsLists(context);
        if (nl.isEmpty()) {
            jo.put(ID, 0);
        } else {
            jo.put(ID, nl.get(nl.size() - 1).getId() + 1);
        }
        // Convert list
        jo.put(NEWS, new JSONArray());
        JSONArray array = jo.getJSONArray(NEWS);
        for (News ne : newslist) {
            JSONObject j = new JSONObject();
            j.put(LINK, ne.getLink());
            j.put(TITLE, ne.getTitle());
            j.put(AUTHOR, ne.getAuthor());
            j.put(DESCRIPTION, ne.getDescription());
            j.put(DATE, ne.getPublishedDate());
            array.put(j);
        }
        json.getJSONArray(NEWS_LIST).put(jo);
        // Write file
        OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME,
                Context.MODE_PRIVATE));
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.write(json.toString());
            pw.flush();
        }
        return LIST_ADDED;
    }

    public static int remove(NewsList newslist, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (newslist.getId() < 0 || newslist.getName().equals("")) {
            return INVALID_LIST;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = new InputStreamReader(context.openFileInput(FILE_NAME));
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
            if (jo.getInt(ID) == newslist.getId()) {
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
            return LIST_REMOVED;
        }
        return LIST_NOT_REMOVED;
    }

    public static int update(NewsList newslist, Context context) throws JSONException, IOException {
        // Check if the news list is invalid
        if (newslist.getId() < 0 || newslist.getName().equals("")) {
            return INVALID_LIST;
        }
        // Read file
        JSONObject json;
        InputStreamReader in = new InputStreamReader(context.openFileInput(FILE_NAME));
        try(Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();
            // Get next line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }
            json = new JSONObject(sb.toString());
        }
        // Get array
        JSONArray array = json.getJSONArray(NEWS_LIST);
        int index = -1;
        for (int i = 0; i < array.length(); i++) {
            JSONObject jo = array.getJSONObject(i);
            if (jo.getInt(ID) == newslist.getId()) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            JSONObject n = array.getJSONObject(index);
            n.put(NAME, newslist.getName());
            JSONArray news = n.getJSONArray(NEWS);
            // Remove all
            while (news.length() > 0) {
                news.remove(0);
            }
            // Update list
            for (News ne : newslist.getNewslist()) {
                JSONObject j = new JSONObject();
                j.put(LINK, ne.getLink());
                j.put(TITLE, ne.getTitle());
                j.put(AUTHOR, ne.getAuthor());
                j.put(DESCRIPTION, ne.getDescription());
                j.put(DATE, ne.getPublishedDate());
                news.put(j);
            }
            // Write file
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(FILE_NAME,
                    Context.MODE_PRIVATE));
            try (PrintWriter pw = new PrintWriter(out)) {
                pw.write(json.toString());
                pw.flush();
            }
            return LIST_UPDATED;
        }
        return LIST_NOT_UPDATED;
    }
}
