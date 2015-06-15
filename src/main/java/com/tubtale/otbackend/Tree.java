package com.tubtale.otbackend;

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

    @Column(name = "ip")
    private String ip;

    @Column(name = "metersToHide")
    private int metersToHide;

    @Column(name = "timestamp")
    private java.sql.Timestamp timestamp;

    public Tree() { }

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

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMetersToHide(){ return this.metersToHide; }
    public void setMetersToHide(int metersToHide) { this.metersToHide = metersToHide; }

    public java.sql.Timestamp getTimestamp(){ return this.timestamp; }
    public void setTimestamp(java.sql.Timestamp timestamp){ this.timestamp = timestamp; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        if (id != tree.getId()) return false;
        if (text != null ? !text.equals(tree.text) : tree.text != null) return false;
        if (ip != null ? !ip.equals(tree.ip) : tree.ip != null) return false;
        if (metersToHide != tree.getMetersToHide()) return false;
        if (timestamp != null ? !timestamp.equals(tree.timestamp) : tree.timestamp != null) return false;

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

