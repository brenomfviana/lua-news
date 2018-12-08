package br.edu.ufrn.brenov.luanews.model;

import java.util.ArrayList;
import java.util.List;

public class NewsList {

    private int id;
    private String name;
    private List<News> newslist;

    public NewsList(int id, String name, List<News> newslist) {
        this.id = id;
        this.name = name;
        this.newslist = newslist;
        if (newslist == null) {
            this.newslist = new ArrayList<>();
        }
    }

    public void add(News news) {
        this.newslist.add(news);
    }

    public void remove(News news) {
        int i = 0;
        for (News n : this.newslist) {
            if (n.getLink().equals(news.getLink())) {
                break;
            }
            i++;
        }
        this.newslist.remove(i);
    }

    public boolean isEmpty() {
        if (this.newslist == null) {
            this.newslist = new ArrayList<>();
        }
        return this.newslist.isEmpty();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNewslist() {
        return this.newslist;
    }

    public void setNewslist(List<News> newslist) {
        this.newslist = newslist;
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", newslist=" + this.newslist +
                '}';
    }
}
