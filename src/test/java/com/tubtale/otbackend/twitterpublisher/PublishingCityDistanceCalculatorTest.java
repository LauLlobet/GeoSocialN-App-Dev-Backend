package com.tubtale.otbackend.twitterpublisher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
/**
 * Created by quest on 07/10/15.
 */
public class PublishingCityDistanceCalculatorTest {


    @Test
    public void getFromBarcelona() {
        float lon=  41.38702799f;
        float lat = 2.17003465f;
        PublishingCityDistanceCalculator calc = new PublishingCityDistanceCalculator(lon,lat);
        assertThat("Barcelona",is(equalTo(calc.getCityName())));
        assertThat("Pl.Catalunya",is(equalTo(calc.getCenterMonument())));
        assertThat(""+0,is(equalTo(""+calc.getDistanceInMeters())));
    }

    @Test
    public void getFromGirona() {
        float lon=  41.8109004f;
        float lat = 2.743285799999967f;
        PublishingCityDistanceCalculator calc = new PublishingCityDistanceCalculator(lon,lat);
        assertThat("Girona",is(equalTo(calc.getCityName())));
        assertThat("La lleona",is(equalTo(calc.getCenterMonument())));
        assertThat("21631",is(equalTo(""+calc.getDistanceInMeters())));
    }


}
