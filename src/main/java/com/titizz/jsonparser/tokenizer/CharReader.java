package com.titizz.jsonparser.tokenizer;

import java.io.IOException;
import java.io.Reader;


public class CharReader {

    private static final int BUFFER_SIZE = 1024;

    private Reader reader;

    private char[] buffer;

    private int pos;

    private int size;

    public CharReader(Reader reader) {
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * Return the character at the pos index and return
     * @return
     */
    public char peek() {
        if (pos - 1 >= size) {
            return (char) -1;
        }

        return buffer[Math.max(0, pos - 1)];
    }

    /**
     * Returns the character at the pos index, adds pos + 1, and finally returns the character
     * @return
     * @throws IOException
     */
    public char next() throws IOException {
        if (!hasMore()) {
            return (char) -1;
        }

        return buffer[pos++];
    }

    public void back() {
        pos = Math.max(0, --pos);
    }

    public boolean hasMore() throws IOException {
        if (pos < size) {
            return true;
        }

        fillBuffer();
        return pos < size;
    }

    void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if (n == -1) {
            return;
        }

        pos = 0;
        size = n;
    }
}
