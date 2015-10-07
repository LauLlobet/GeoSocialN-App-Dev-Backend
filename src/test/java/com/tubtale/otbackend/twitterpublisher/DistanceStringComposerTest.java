package com.tubtale.otbackend.twitterpublisher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by quest on 07/10/15.
 */
public class DistanceStringComposerTest {


    @Test
    public void composeAfarPlace() {
        float lon=  41.8109004f;
        float lat = 2.743285799999967f;
        DistanceStringComposer composer = new DistanceStringComposer(lon, lat);
        assertThat("21Km de Girona",is(equalTo(composer.toString())));
    }


    @Test
    public void composeANearPlace() {
        float lon= 41.988180f;
        float lat = 2.82408f;
        DistanceStringComposer composer = new DistanceStringComposer(lon, lat);
        assertThat("10m de La lleona",is(equalTo(composer.toString())));
    }
}
