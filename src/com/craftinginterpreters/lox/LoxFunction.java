package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
  private final Expr.Function declaration; //Chapter 10 Challenge 2
  private final Environment closure;
  private final String name; //Chapter 10 Challenge 2
	
  //Chapter 10 Challenge 2
  LoxFunction(String name, Expr.Function declaration, Environment closure) {
    this.closure = closure;
    this.declaration = declaration;
	this.name = name; //Chapter 10 Challenge 2
  }
	
  @Override
  public String toString() {
	//Chapter 10 Challenge 2
	if(name == null) return "<fn>";
	
    return "<fn>";
  }
	
  @Override
  public int arity() {
    return declaration.params.size();
  }

  @Override
  public Object call(Interpreter interpreter,
                     List<Object> arguments) {
    Environment environment = new Environment(closure);
    for (int i = 0; i < declaration.params.size(); i++) {
      environment.define(declaration.params.get(i).lexeme,
          arguments.get(i));
    }

    try {
      interpreter.executeBlock(declaration.body, environment);
    } catch (Return returnValue) {
      return returnValue.value;
    }
    return null;
  }

}