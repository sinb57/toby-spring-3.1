package toby.springbook.learning;

import java.io.BufferedReader;
import java.io.IOException;

public interface LineCallback {
    Integer doSomethingWithReader(String line, Integer value) throws IOException;
}


