package util.AST;

import checker.SemanticException;

public class Term extends AST{
	
	Factor fator = null;
	public ArithmeticOperator op = null;
	Term termo = null;
	
	public Term(Factor fator, ArithmeticOperator op, Term termo) {
		this.fator = fator;
		this.op = op;
		this.termo = termo;
	}

	public Term(Factor fator) {
		super();
		this.fator = fator;
	}
	
	public Factor getFator() {
		return fator;
	}

	public Term getTermo() {
		return termo;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitTerm(this, args);
	}

}
