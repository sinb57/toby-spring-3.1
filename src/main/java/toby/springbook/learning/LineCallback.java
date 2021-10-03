package toby.springbook.learning;

import java.io.IOException;

public interface LineCallback<T> {
    T doSomethingWithReader(String line, T value);
}


