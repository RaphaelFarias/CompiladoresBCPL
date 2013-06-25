package util.AST;

import checker.SemanticException;

public class ComparacaoNumero extends AST {
	ArithmeticExpression expDir = null;
	public RelationalOperator relacionalOp = null;
	ArithmeticExpression expEsq = null;
	
	public ComparacaoNumero(ArithmeticExpression expDir,
			RelationalOperator relacionalOp, ArithmeticExpression expEsq) {
		super();
		this.expDir = expDir;
		this.relacionalOp = relacionalOp;
		this.expEsq = expEsq;
	}

	public ArithmeticExpression getExpDir() {
		return expDir;
	}

	public ArithmeticExpression getExpEsq() {
		return expEsq;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitComparacaoNumero(this, args);
	}

}
