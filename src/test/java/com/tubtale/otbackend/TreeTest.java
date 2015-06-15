package com.tubtale.otbackend;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TreeTest extends CommonTest{


    @Test
    public void textShouldHaveSetterAndGetter() {
        String expected = "test text";
        tree.setText(expected);
        assertThat(tree.getText(), is(equalTo(expected)));
    }

    @Test
    public void equalsShouldFailIfIpIsNotTheSameAgainstDefault() {
        Tree actual = new Tree();
        actual.setText(text);
        actual.setIp("20.30.20.30.03");
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldFailIfTextIsNotTheSameAgainstDefault() {
        Tree actual = new Tree();
        actual.setText("not the same");
        tree.setText(null);
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldReturnFalseIfMetersToHideIsNotTheSameAgainstDefault() {
        Tree actual = new Tree();
        actual.setMetersToHide(4);
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldFailIfTimestampIsNotTheSameAgainstDefault() {
        Tree actual = new Tree();
        actual.setText(text);
        actual.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        assertThat(actual, is(not(equalTo(tree))));
    }
    //------------------
    @Test
    public void equalsShouldFailIfIpIsNotTheSameAgainstVal() {
        Tree actual = new Tree();
        actual.setText(text);
        tree.setIp("sssssss");
        actual.setIp("20.30.20.30.03");
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldFailIfTextIsNotTheSameAgainstVal() {
        Tree actual = new Tree();
        tree.setText("original text");
        actual.setText("not the same");
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldReturnFalseIfMetersToHideIsNotTheSameAgainstVal() {
        Tree actual = new Tree();
        actual.setMetersToHide(4);
        tree.setMetersToHide(3);
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldFailIfTimestampIsNotTheSameAgainstVal() {
        Tree actual = new Tree();
        tree.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis() - 100));
        actual.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        actual.setText(text);
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalShouldReturnTrueIfAllIsEqual() {
        Tree actual = new Tree();
        actual.setText(text);
        tree.setText(text);
        actual.setTimestamp(new java.sql.Timestamp(100));
        tree.setTimestamp(new java.sql.Timestamp(100));
        actual.setMetersToHide(20);
        tree.setMetersToHide(20);
        actual.setIp("A");
        tree.setIp("A");
        assertThat(actual, is(equalTo(tree)));
    }


    @Test
    public void sqlTimestampTest() {
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        assertThat(timestamp.getTime(),is(equalTo(time)));
    }

}
