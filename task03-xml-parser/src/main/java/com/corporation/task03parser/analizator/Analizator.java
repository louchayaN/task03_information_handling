package com.corporation.task03parser.analizator;

import java.io.IOException;

import com.corporation.task03parser.entity.Node;

public interface Analizator{

	Node getNextNode() throws IOException;
}
