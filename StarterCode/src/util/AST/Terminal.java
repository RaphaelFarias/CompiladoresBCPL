package util.AST;

import checker.SemanticException;

public class Terminal extends AST {
	public String spelling;
	
	public Terminal(String spelling){
		this.spelling = spelling;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return null;
	}
	
}
