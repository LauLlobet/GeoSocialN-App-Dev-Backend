package com.tubtale.otbackend.twitterpublisher;

/**
 * Created by quest on 07/10/15.
 */
public class DistanceStringComposer {
    NearestCityDistanceCalculator distanceCalculator;
    Boolean isFurtherThanNKm = false;
    int metersToBeFurtherThanNKm = 4000;

    public DistanceStringComposer(float longitude, float latitude) {
        distanceCalculator = new NearestCityDistanceCalculator(longitude,latitude);
        if(distanceCalculator.getDistanceInMeters() > metersToBeFurtherThanNKm){
            isFurtherThanNKm = true;
        }
    }
    @Override
    public String toString() {
        if(isFurtherThanNKm){
            return this.stringWhenIsFutherThanOneKm();
        }else{
            return this.stringWhenIsNotFutherThanOneKm();
        }
    }

    private String stringWhenIsFutherThanOneKm() {
        String ans = "";
        ans += distanceCalculator.getDistanceInKm() + "Km";
        ans += " de "+ distanceCalculator.getCityName();
        return ans;
    }

    private String stringWhenIsNotFutherThanOneKm() {
        String ans = "";
        ans += distanceCalculator.getDistanceInMeters() + "m";
        ans += " de "+ distanceCalculator.getCenterMonument();
        return ans;
    }
}
