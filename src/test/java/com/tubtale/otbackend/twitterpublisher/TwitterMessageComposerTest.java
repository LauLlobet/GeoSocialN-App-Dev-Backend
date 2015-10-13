package com.tubtale.otbackend.twitterpublisher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by quest on 03/10/15.
 */
public class TwitterMessageComposerTest {


    /*
        get list of users
     */

    float lon = 1;
    float lat = 2;
    int treeId = 123456;
/*
    @Test
    public void getListOfUsersToNotify() {

        String text = "hola hola @user1 hola @user2 coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(3,is(equalTo(composer.getUsersToNotify().size())));
    }

    @Test
    public void getListOfUsersToNotifyOneByOne() {
        String text = "hola hola @user1 hola @user2 coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat("user1",is(equalTo(composer.getUsersToNotify().get(0))));
        assertThat("user2",is(equalTo(composer.getUsersToNotify().get(1))));
        assertThat("user3",is(equalTo(composer.getUsersToNotify().get(2))));
    }

    @Test
    public void getTextBeforeLock() {
        String text = "hola hola @user1 hola @user2 *lock coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1 hola user2")));
    }

    @Test
    public void getTextBeforeBury() {
        String text = "hola hola @user1*bury hola @user2 coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1")));
    }

    @Test
    public void getTextBeforeLockWithAbury() {
        String text = "hola hola @user1 hola @user2 *lock coco *bury @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1 hola user2")));
    }

    @Test
    public void getTextBeforeBuryWithALock() {
        String text = "hola hola @user1*bury hola @user2 *lock coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1")));
    }

    @Test
    public void getTextBeforeLockWithALock() {
        String text = "hola hola @user1 hola @user2 *lock coco *lock @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1 hola user2")));
    }

    @Test
    public void getTextBeforeBuryWithABury() {
        String text = "hola hola @user1*bury hola @user2 *bury coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1")));
    }

    @Test
    public void getTextBeforeLockOrBuryWithoutLockOrBuryAndTrim() {
        String text = " hola hola @user1 hola @user2 coco @user3 ";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola user1 hola user2 coco user3")));
        text = "hola hola @user1 hola @user2 near*lock coco @user3";
        composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat("hola hola user1 hola user2 near",is(equalTo(composer.getTextbeforeLockOrBury())));
    }

    @Test
    public void getTextBegginingWithLock() {
        String text = "*hola hola @user1*bury hola @user2 *bury coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("")));
    }

    @Test
    public void getTextBegginingWithSpaceAndLock() {
        String text = " * hola hola @user1*bury hola @user2 *bury coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("")));
    }*/

    @Test
    public void getTextBoldAndSubstringed() {
        String text = "abcdefghijklmnopqrst";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBuryAndBoldAndSubstring(10),is(equalTo("\uD835\uDC1A\uD835\uDC1B\uD835\uDC1C\uD835\uDC1D\uD835\uDC1E\uD835\uDC1F\uD835\uDC20\uD835\uDC21\uD835\uDC22\uD835\uDC23")));

        text = "abcdefghijklmnopqrst";
        composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat(composer.getTextbeforeLockOrBuryAndBoldAndSubstring(11),is(equalTo("\uD835\uDC1A\uD835\uDC1B\uD835\uDC1C\uD835\uDC1D\uD835\uDC1E\uD835\uDC1F\uD835\uDC20\uD835\uDC21\uD835\uDC22\uD835\uDC23\uD835\uDC24")));
    }


/*
    @Test
    public void getSizesOfDedicatedTextNormalUsername() throws Exception {
        lon = 2.1880345344543457f;
        lat = 41.38018798828125f;                                                     //Hola peña este mensaje es para decirte que aqui hay rovellones us
        String text = "Hola peña este mensaje es para decirte que aqui hay rovellones @user1 hola @user2 coco @user3";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat("@user1 a 21Km de Girona te han escrito un mensaje secreto, descubrelo:www.wiressly.com/123456",
                is(equalTo(composer.getTweets().get(0))));
    }

    @Test
    public void getSizesOfDedicatedTextLongUsername() throws Exception {
        lon=  41.8109004f;
        lat = 2.743285799999967f;                                                     //Hola peña este mensaje es para decirte que aqui hay rovellones us
        String text = " * Hola peña este mensaje es para decirte que aqui hay rovellones @user1 hola @user2 coco @user3 ";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat("@user1 a 21Km de Girona te han escrito un mensaje secreto, descubrelo:www.wiressly.com/123456",
                is(equalTo(composer.getTweets().get(0))));
    }

    @Test
    public void getSizesOfDedicatedTextExtremelyLongUsername() throws Exception {
        lon=  41.8109004f;
        lat = 2.743285799999967f; //  "Expected :@user1 a 21Km de Girona te han escrito:\"Hola peña este mensaje es para decirte que aqui hay rovellones us\" descubrelo:www.wiressly.com/123456",
        String text =                " @user1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa hola ";
        TwitterMessageComposer composer = new TwitterMessageComposer(text,lon,lat,treeId);
        assertThat("@user1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa www.wiressly.com/123456",
                is(equalTo(composer.getTweets().get(0))));
    }

*/

}
