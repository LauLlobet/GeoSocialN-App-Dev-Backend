package com.tubtale.otbackend;

/**
 * Created by quest on 12/06/15.
 */


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Tree")
@XmlRootElement

public class Tree {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    int id;


    @Column(name = "text")
    private String text;

    public Tree() { }
    public Tree(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        if (id != tree.getId()) return false;
        if (text != null ? !text.equals(tree.text) : tree.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        //long temp;
        result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        //temp = Double.doubleToLongBits(price);
        //result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

