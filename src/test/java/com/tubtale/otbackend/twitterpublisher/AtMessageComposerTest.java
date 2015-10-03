package com.tubtale.otbackend.twitterpublisher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by quest on 03/10/15.
 */
public class AtMessageComposerTest {


    /*
        get list of users
     */

    @Test
    public void getListOfUsersToNotify() {
        String text = "hola hola @user1 hola @user2 coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(3,is(equalTo(composer.getUsersToNotify().size())));
    }

    @Test
    public void getListOfUsersToNotifyOneByOne() {
        String text = "hola hola @user1 hola @user2 coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat("user1",is(equalTo(composer.getUsersToNotify().get(0))));
        assertThat("user2",is(equalTo(composer.getUsersToNotify().get(1))));
        assertThat("user3",is(equalTo(composer.getUsersToNotify().get(2))));
    }

    @Test
    public void getTextBeforeLock() {
        String text = "hola hola @user1 hola @user2 *lock coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1 hola @user2")));
    }

    /*
        get text before bury
     */

    @Test
    public void getTextBeforeBury() {
        String text = "hola hola @user1*bury hola @user2 coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1")));
    }

    @Test
    public void getTextBeforeLockWithAbury() {
        String text = "hola hola @user1 hola @user2 *lock coco *bury @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1 hola @user2")));
    }

    @Test
    public void getTextBeforeBuryWithALock() {
        String text = "hola hola @user1*bury hola @user2 *lock coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1")));
    }

    @Test
    public void getTextBeforeLockWithALock() {
        String text = "hola hola @user1 hola @user2 *lock coco *lock @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1 hola @user2")));
    }

    @Test
    public void getTextBeforeBuryWithABury() {
        String text = "hola hola @user1*bury hola @user2 *bury coco @user3";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1")));
    }

    @Test
    public void getTextBeforeLockOrBuryWithoutLockOrBuryAndTrim() {
        String text = " hola hola @user1 hola @user2 coco @user3 ";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat(composer.getTextbeforeLockOrBury(),is(equalTo("hola hola @user1 hola @user2 coco @user3")));
        text = "hola hola @user1 hola @user2 near*lock coco @user3";
        composer = new AtMessageComposer(text);
        assertThat("hola hola @user1 hola @user2 near",is(equalTo(composer.getTextbeforeLockOrBury())));
    }


    /*
        Create prhrases to publish at twitter
     */

/*
   te escribieron
   te han escrito
        Pl. Cat.
    300m de Pl.Catalunya
    1km  de Pl.Catalunya
    32km de Pl.Catalunya
    40km de Barcelona
    40000km de Barcelona

 */
    @Test
    public void getSizesOfDedicatedText() {
        String text = " hola hola @user1 hola @user2 coco @user3 ";
        AtMessageComposer composer = new AtMessageComposer(text);
        assertThat("@user1 a 211km de BCN han escrito para ti:\"Hola juan este mensaje es \" descubrelo:",is(equalTo(composer.getTweet())));
    }
}
