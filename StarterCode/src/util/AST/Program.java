package util.AST;

import java.util.*;

import checker.SemanticException;

public class Program extends AST {
	String token;
	public ArrayList<Command> c;
	public ArrayList<Function> func;
	public ArrayList<Procedure> proc;
	
	public String toString(int level){
		return (super.getSpaces(level) + token);
	}
	
	public Program(){
		this.token = "Program";
	}
	
	public Program(ArrayList<Command> c, ArrayList<Function> v, ArrayList<Procedure> proc){
		this.token = "Program";
		this.c = c;
		this.func = v;
		this.proc = proc;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitProgram(this, args);
	}
}
