package com.tubtale.otbackend;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TreeTest extends CommonTest{

    @Test
    public void idShouldHaveSetterAndGetter() {
        int expected = 123;
        tree.setId(expected);
        assertThat(tree.getId(), is(equalTo(expected)));
    }

    @Test
    public void textShouldHaveSetterAndGetter() {
        String expected = "test text";
        tree.setText(expected);
        assertThat(tree.getText(), is(equalTo(expected)));
    }

    @Test
    public void equalsShouldFailIfIdIsNotTheSame() {
        Tree actual = new Tree(123);
        actual.setText(text);
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalsShouldReturnFalseIfTextIsNotTheSame() {
        Tree actual = new Tree(id);
        actual.setText("new text");
        assertThat(actual, is(not(equalTo(tree))));
    }

    @Test
    public void equalShouldReturnTrueIfIdTextEqual() {
        Tree actual = new Tree(id);
        actual.setText(text);
        assertThat(actual, is(equalTo(tree)));
    }

}
