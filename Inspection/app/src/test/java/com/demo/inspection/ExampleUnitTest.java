package com.demo.inspection;

import com.demo.inspection.utils.Tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public  class ExampleUnitTest {
    Tools tools=new Tools();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        tools.CheckIP("123.123.123.123");
        tools.CheckIP("0.0.0.0");
        tools.CheckIP("1.1.1.1");
        tools.CheckIP("1.1");
        tools.CheckIP("1.1.1");
        tools.CheckIP("256.1.1.0");


    }
}