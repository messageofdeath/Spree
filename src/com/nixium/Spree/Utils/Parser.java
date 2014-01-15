package com.nixium.Spree.Utils;

import java.util.ArrayList;

import com.nixium.Spree.Spree;

import me.messageofdeath.Blocks.Vector;

public class Parser {
	
	private Spree instance;
	
	public Parser(Spree instance) {
		this.instance = instance;
	}
	
	public String parseSpawnsToString(ArrayList<Vector> spawns) {
		if(!spawns.isEmpty() && spawns != null) {
			String endResult = "";
			for(Vector vector : spawns) {
				endResult += Vector.getVectorToString(vector) + "|";
			}
			return endResult.substring(0, endResult.length() - 1);
		}else{
			this.instance.logError("Parser Spawns to String", "Parser", "parseSpawnsToString(ArrayList<Vector> spawns)"
					, "There are no spawns to convert.");
			return "";
		}
	}
	
	public ArrayList<Vector> parseStringToSpawns(String parse) {
		if(parse != null && !parse.isEmpty()) {
			ArrayList<Vector> endResult = new ArrayList<Vector>();
			for(String vector : parse.split("\\|")) {
				endResult.add(Vector.getStringToVector(vector));
			}
			return endResult;
		}else{
			this.instance.logError("Parser String to Spawns", "Parser", "parseStringtoSpawns(String parse)",
					"There are no spawns to convert.");
			return new ArrayList<Vector>();
		}
	}
}
