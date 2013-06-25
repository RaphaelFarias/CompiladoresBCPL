package util.AST;

import checker.SemanticException;

public class BoolValue extends Terminal{

	public BoolValue(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitBoolValue(this, args);
	}

}
