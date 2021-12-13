package com.ph.object;

import java.io.FilterInputStream;
import java.io.InputStream;

public class InputStreamTest extends FilterInputStream {
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param in the underlying input stream, or <code>null</code> if
     *           this instance is to be created without an underlying stream.
     */
    protected InputStreamTest(InputStream in) {
        super(in);
    }


}
