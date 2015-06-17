package com.tubtale.otbackend;

import org.hibernate.annotations.Type;
import org.postgis.Point;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Tree")
@XmlRootElement

public class Tree {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "text", nullable = true)
    private String text;

    @Column(name = "ip", nullable = true)
    private String ip;

    @Column(name = "metersToHide", nullable = true)
    private int metersToHide;

    @Column(name = "timestamp", nullable = true)
    private java.sql.Timestamp timestamp;

    @Type(type="org.hibernate.spatial.GeometryType")
    @Column(name = "location", nullable = true)
    private Point location;

    public Tree() {
        id = null;
        setLocation(110,110);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocation(float longitude, float latitude){
        this.location = new Point(longitude,latitude);
    }

    public Point getLocation(){
        return location;
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

        if (!id.equals(tree.getId())) return false;
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

