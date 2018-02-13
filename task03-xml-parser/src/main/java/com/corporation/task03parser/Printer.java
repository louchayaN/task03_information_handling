package com.corporation.task03parser;

import com.corporation.task03parser.entity.Node;

public class Printer {

	public static void printToConsole(Node node){
		
		System.out.printf("Node name: %s - %s%n", node.getName(), node.getType());
	}
	
}
