package toby.springbook;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    static JUnitTest testObject;

    @Test
    public void test1() {
        Assertions.assertThat(this).isNotEqualTo(testObject);
        System.out.println("testObject = " + testObject);
        System.out.println("this = " + this);
        testObject = this;
    }

    @Test
    public void test2() {
        Assertions.assertThat(this).isNotEqualTo(testObject);
        System.out.println("testObject = " + testObject);
        System.out.println("this = " + this);
        testObject = this;
    }

    @Test
    public void test3() {
        Assertions.assertThat(this).isNotEqualTo(testObject);
        System.out.println("testObject = " + testObject);
        System.out.println("this = " + this);
        testObject = this;
    }


}
