package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Writef extends Command {
	public Expressao exp = null;
	String str;
	
	public Writef(Expressao id) {
		this.str = "Writef";
		this.exp = id;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public Expressao getExp() {
		return exp;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitWritef(this, args);
	}
}
