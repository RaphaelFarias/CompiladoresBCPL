package util.AST;

import checker.SemanticException;

public class ComparacaoBooleana extends AST {
	public BoolValue boolValDir = null;
	public BoolValue boolValEsq = null;
	public ComparativeOperator op = null;
	public ArithmeticExpression expDir = null;
	public ArithmeticExpression expEsq = null;
	
	public ComparacaoBooleana(ArithmeticExpression expEsq, ComparativeOperator op, ArithmeticExpression expDir) {
		super();
		this.op = op;
		this.expDir = expDir;
		this.expEsq = expEsq;
	}

	public ComparacaoBooleana(ArithmeticExpression expEsq, ComparativeOperator op, BoolValue boolValDir) {
		super();
		this.boolValDir = boolValDir;
		this.op = op;
		this.expEsq = expEsq;
	}

	public ComparacaoBooleana(BoolValue boolValEsq,ComparativeOperator op, BoolValue boolValDir) {
		super();
		this.boolValDir = boolValDir;
		this.boolValEsq = boolValEsq;
		this.op = op;
	}

	public ComparacaoBooleana(BoolValue boolValEsq, ComparativeOperator op,	ArithmeticExpression expDir) {
		super();
		this.boolValEsq = boolValEsq;
		this.op = op;
		this.expDir = expDir;
	}

	public ComparacaoBooleana(BoolValue boolValEsq) {
		super();
		this.boolValEsq = boolValEsq;
	}
	

	public BoolValue getBoolValDir() {
		return boolValDir;
	}

	public BoolValue getBoolValEsq() {
		return boolValEsq;
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
			return v.visitComparacaoBooleana(this, args);
		}

	
}
