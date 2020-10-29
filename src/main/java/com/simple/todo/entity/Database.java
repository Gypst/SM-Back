package com.simple.todo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	public static int counterOfLists = 12;
	public static List<Map<String, String>> bdEmu = new ArrayList<Map<String, String>>() {{
		add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message deal"); }});
		add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message deal"); }});
		add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message deal"); }});
		add(new HashMap<String, String>() {{ put("id", "4"); put("text", "Buy milk"); }});
		add(new HashMap<String, String>() {{ put("id", "5"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "6"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "7"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "8"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "9"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "10"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "11"); put("text", "Take over the world"); }});
		add(new HashMap<String, String>() {{ put("id", "12"); put("text", "Fin"); }});
	}};
}
