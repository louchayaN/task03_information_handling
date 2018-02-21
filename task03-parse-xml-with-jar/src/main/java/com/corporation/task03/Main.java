package com.corporation.task03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.corporation.task03parser.analizator.NodeAnalizator;
import com.corporation.task03parser.entity.Node;

public class Main {

	private static final Logger lOGGER = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {

		try {
			InputStream stream = new FileInputStream ("c:/Java/projects/books.xml");
			NodeAnalizator nodeAnalizator = new NodeAnalizator(stream);
		
			Node node;
			while((node = nodeAnalizator.getNextNode()) != null) {
				System.out.println(node.getName() + " - " + node.getType());
			}
				
		} catch (FileNotFoundException e) {
			lOGGER.warning("File haven't been opened");
		} catch (UnsupportedEncodingException e) {
			lOGGER.warning("Wrong default encording - UTF-8");
		} catch (IOException e) {
			lOGGER.warning("Exception during reading the file");
		}	
	} 
		

}
