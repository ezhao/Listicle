package com.herokuapp.ezhao.listicle;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Listicle")
public class Listicle extends Model {
    @Column(name = "listName") public String listName;
    @Column(name = "object") public String object;

    public Listicle() {
        super();
    }

    public Listicle(String listName, String object) {
        super();
        this.listName = listName;
        this.object = object;
    }

    public String getListName() {
        return listName;
    }

    @Override
    public String toString() {
        return object;
    }
}
