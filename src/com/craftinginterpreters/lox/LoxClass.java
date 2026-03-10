package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Map;

class LoxClass implements LoxCallable {
  final String name;
  final List<LoxClass> superclasses; //Chapter 13 Challenge 1
  private final Map<String, LoxFunction> methods;

  //Chapter 13 Challenge 1
  LoxClass(String name, List<LoxClass> superclasses,
           Map<String, LoxFunction> methods) {
    this.superclasses = superclasses;
    this.name = name;
    this.methods = methods;
  }

  LoxFunction findMethod(String name) {
    if (methods.containsKey(name)) {
      return methods.get(name);
    }
	  
	//Chapter 13 Challenge 1
	for(LoxClass superclass : superclasses){
		LoxFunction method = superclass.findMethod(name);
		if (method != null){
			return method;
		}
	}

    return null;
  }
	
  @Override
  public String toString() {
    return name;
  }
	
  @Override
  public Object call(Interpreter interpreter,
                     List<Object> arguments) {
    LoxInstance instance = new LoxInstance(this);
    LoxFunction initializer = findMethod("init");
    if (initializer != null) {
      initializer.bind(instance).call(interpreter, arguments);
    }
	  
    return instance;
  }

  @Override
  public int arity() {
    LoxFunction initializer = findMethod("init");
    if (initializer == null) return 0;
    return initializer.arity();
  }
}