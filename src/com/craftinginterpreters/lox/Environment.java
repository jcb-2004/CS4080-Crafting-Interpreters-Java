package com.craftinginterpreters.lox;

import java.util.ArrayList; //Chapter 11 Challenge 4
import java.util.HashMap;
import java.util.List; //Chapter 11 Challenge 4
import java.util.Map;

class Environment {
  final Environment enclosing;
  private final Map<String, Object> values = new HashMap<>();
  private final List<Object> slots = new ArrayList<>(); //Chapter 11 Challenge 4
	
  Environment() {
    enclosing = null;
  }

  Environment(Environment enclosing) {
    this.enclosing = enclosing;
  }
	
  Object get(Token name) {
    if (values.containsKey(name.lexeme)) {
      return values.get(name.lexeme);
    }
	  
    if (enclosing != null) return enclosing.get(name);

    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }
	
  void assign(Token name, Object value) {
    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);
      return;
    }
	  
    if (enclosing != null) {
      enclosing.assign(name, value);
      return;
    }

    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }

  void define(String name, Object value) {
    values.put(name, value);
	slots.add(value); //Chapter 11 Challenge 4
  }

	//Chapter 11 Challenge 4
	Object getAtIndex(int distance, int index){
		return ancestor(distance).slots.get(index);
	}
	
  Environment ancestor(int distance) {
    Environment environment = this;
    for (int i = 0; i < distance; i++) {
      environment = environment.enclosing; 
    }

    return environment;
  }
	
  Object getAt(int distance, String name) {
    return ancestor(distance).values.get(name);
  }
	
	//Chapter 11 Challenge 4
	void assignAtIndex(int distance, int index, Object value) {
		ancestor(distance).slots.set(index, value);
	}
	
}