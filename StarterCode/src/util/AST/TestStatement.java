package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class TestStatement extends Command {
	Expressao exp = null;
	ArrayList<Command> ThenCmd = null;
	ArrayList<Command> ElseCmd = null;
	
	
	public TestStatement(Expressao exp, ArrayList<Command> ThenCmd, ArrayList<Command> ElseCmd){
		this.exp = exp;
		this.ThenCmd = ThenCmd;
		this.ElseCmd = ElseCmd;
	}
	
	public Expressao getExp() {
		return exp;
	}

	public ArrayList<Command> getThenCmd() {
		return ThenCmd;
	}

	public ArrayList<Command> getElseCmd() {
		return ElseCmd;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitTestStatement(this, args);
	}
}
