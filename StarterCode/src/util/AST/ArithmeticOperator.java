package util.AST;

import checker.SemanticException;

public class ArithmeticOperator extends Terminal{
	
	public ArithmeticOperator(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}

	String opArit;

	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitArithmeticOperator(this, args);
	}
}
