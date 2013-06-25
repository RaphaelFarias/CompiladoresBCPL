package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class IfStatement extends Command {
	
	Expressao exp = null;
	ArrayList<Command> DoCmd = null;
	
	
	public IfStatement(){
		
	}
	
	public Expressao getExp() {
		return exp;
	}

	public ArrayList<Command> getDoCmd() {
		return DoCmd;
	}
	
	public IfStatement(Expressao exp, ArrayList<Command> DoCmd){
		this.exp = exp;
		this.DoCmd = DoCmd;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitIfStatement(this, args);
	}
}
