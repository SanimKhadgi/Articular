package com.sanim.articular.Model;

public class Article {
    public Long id;
    public String Content1;
    public String Content2;
    public String Image1;
    public String Image2;
    public String Image3;
    public String Name;

    public Article() {
    }

    public Article(Long id, String content1, String content2, String image1, String image2, String image3, String name) {
        this.id = id;
        Content1 = content1;
        Content2 = content2;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
