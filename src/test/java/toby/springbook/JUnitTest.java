package toby.springbook;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTest {

    static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();

    @Test
    public void test1() {
        assertThat(this).isNotIn(testObjects);
        System.out.println("this = " + this);
        testObjects.add(this);
    }

    @Test
    public void test2() {
        assertThat(this).isNotIn(testObjects);
        System.out.println("this = " + this);
        testObjects.add(this);
    }

    @Test
    public void test3() {
        assertThat(this).isNotIn(testObjects);
        System.out.println("this = " + this);
        testObjects.add(this);
    }


}
