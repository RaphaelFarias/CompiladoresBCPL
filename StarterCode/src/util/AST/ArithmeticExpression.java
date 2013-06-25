package util.AST;

import checker.SemanticException;

public class ArithmeticExpression extends Expressao {
	Term term = null;
	ArithmeticOperator opAritmetico = null;
	ArithmeticExpression exp = null;
		
	public ArithmeticExpression (Term term, ArithmeticExpression exp, ArithmeticOperator opAritmetico){
		this.opAritmetico = opAritmetico;
		this.exp = exp;
		this.term = term;
	}
	
	public ArithmeticExpression(Term term) {
		super();
		this.term = term;
	}


	public Term getTerm() {
		return term;
	}


	public ArithmeticOperator getOpAritmetico() {
		return opAritmetico;
	}

	public ArithmeticExpression getExp() {
		return exp;
	}


	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitArithmeticExpression(this, args);
	}
	
}
