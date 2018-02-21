package com.corporation.task03parser.reader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public abstract class Reader implements Closeable {

	private InputStream stream;

	public Reader() {
	}

	public Reader(InputStream stream) {
		this.stream = stream;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public abstract String readNextNode() throws IOException;

	public abstract void close() throws IOException;

}
