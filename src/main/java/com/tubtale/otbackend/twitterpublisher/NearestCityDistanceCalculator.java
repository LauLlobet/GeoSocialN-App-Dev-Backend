package com.tubtale.otbackend.twitterpublisher;

import com.tubtale.otbackend.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import java.util.List;
import java.util.Map;

public class NearestCityDistanceCalculator {

    private final String cityName;
    private final String centerMonument;
    private final int distanceInMeters;
    float longitude;
    float latitude;

    public NearestCityDistanceCalculator(float longitude, float latitude){
        this.longitude = longitude;
        this.latitude = latitude;
        Map<String,Object>result = makeQuery(longitude,latitude);
        this.cityName = (String)result.get("cityname");
        this.centerMonument = (String)result.get("centermonument");
        this.distanceInMeters = ((Double)result.get("distanceinmeters")).intValue();
    }

    private Map<String,Object> makeQuery(double longitude, double latitude) {
        Session session = HibernateUtil.getSession();
        List<Map<String,Object>> result;
        Query query = session.createSQLQuery(
                "SELECT cityName, centerMonument," +
                        " ST_distance_sphere(location,ST_GeomFromText('POINT("+longitude+" "+latitude+")', 4326)) as distanceInMeters"+
                        " FROM Cities"+
                        " ORDER BY location <-> st_setsrid(st_makepoint("+longitude+","+latitude+"),4326)"+
                        " LIMIT 1;"
        );
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        result = query.list();
        session.flush();
        session.close();
        return result.get(0);
    }

    public int getDistanceInMeters() {
        return distanceInMeters;
    }

    public String getCenterMonument() {
        return centerMonument.trim();
    }

    public String getCityName() {
        return cityName.trim();
    }

    public int getDistanceInKm() {
        return Math.round(this.getDistanceInMeters() / 1000);
    }

}
