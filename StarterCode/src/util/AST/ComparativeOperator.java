package util.AST;

import checker.SemanticException;

public class ComparativeOperator extends Terminal{

	public ComparativeOperator(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitComparativeOperator(this, args);
	}

}
