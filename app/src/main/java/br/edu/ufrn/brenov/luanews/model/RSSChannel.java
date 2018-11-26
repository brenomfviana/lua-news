package br.edu.ufrn.brenov.luanews.model;

public class RSSChannel {

    private String link;
    private String name;

    public RSSChannel(String link, String name) {
        this.link = link;
        this.name = name;
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

    @Override
    public String toString() {
        return "RSSChannel{" +
                "link='" + this.link + '\'' +
                ", name='" + this.name + '\'' +
                '}';
    }
}
