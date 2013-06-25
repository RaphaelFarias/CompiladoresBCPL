package util.AST;

import checker.SemanticException;

public class RelationalOperator extends Terminal {
	
	public RelationalOperator(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitRelationalOperator(this, args);
	}

}
