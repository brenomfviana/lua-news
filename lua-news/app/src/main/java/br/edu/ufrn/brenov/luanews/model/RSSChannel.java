package br.edu.ufrn.brenov.luanews.model;

public class RSSChannel {

    private String link;
    private String name;
    private boolean fav;

    public RSSChannel(String link, String name, boolean fav) {
        this.link = link;
        this.name = name;
        this.fav = fav;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    public String toString() {
        return "RSSChannel{" +
                "link='" + this.link + '\'' +
                ", name='" + this.name + '\'' +
                ", fav=" + this.fav +
                '}';
    }
}
