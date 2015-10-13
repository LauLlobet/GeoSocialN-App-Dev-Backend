package com.tubtale.otbackend.utils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StringToUnicodeBoldConverterTest {
    @Test
    public void convertASingleChar() {
        String ans;
        ans = StringToUnicodeBoldConverter.convertASingleChar('A');
        assertThat(ans, is(equalTo("\uD835\uDC00")));
        ans = StringToUnicodeBoldConverter.convertASingleChar('B');
        assertThat(ans, is(equalTo("\uD835\uDC01")));
    }
    @Test
    public void throwsExcpetionWhenConvertASingleCharNonNormal() {
        try {
            StringToUnicodeBoldConverter.convertASingleChar(';');
        } catch(Exception e){
            assertThat("Ok", is(equalTo("Ok")));
            return;
        }
        assertThat("Ok", is(equalTo("NoOk")));
    }
    @Test
    public void convertASingleCharLowercaseChar() {
        String ans;
        ans = StringToUnicodeBoldConverter.convertASingleChar('a');
        assertThat(ans, is(equalTo("\uD835\uDC1A")));
        ans = StringToUnicodeBoldConverter.convertASingleChar('b');
        assertThat(ans, is(equalTo("\uD835\uDC1B")));
    }

    @Test
    public void convertStringWithLowerAndUpperCases() {
        String request = "Hola John Doe";
        String ans = "\uD835\uDC07\uD835\uDC28\uD835\uDC25\uD835\uDC1A \uD835\uDC09\uD835\uDC28\uD835\uDC21\uD835\uDC27 \uD835\uDC03\uD835\uDC28\uD835\uDC1E";
        assertThat(ans, is(equalTo(StringToUnicodeBoldConverter.convertString(request, request.length()))));
    }

    @Test
    public void convertStringWithLowerAndUpperCasesAndStrangeCharacters() {
        String request = "Hola John Doe;!!";
        String ans = "\uD835\uDC07\uD835\uDC28\uD835\uDC25\uD835\uDC1A \uD835\uDC09\uD835\uDC28\uD835\uDC21\uD835\uDC27 \uD835\uDC03\uD835\uDC28\uD835\uDC1E;!!";
        assertThat(ans, is(equalTo(StringToUnicodeBoldConverter.convertString(request, request.length()))));
    }
}
