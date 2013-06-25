package util.AST;

import checker.SemanticException;

public class Factor extends AST {
	Identifier id = null;
	Numero numero = null;
	ArithmeticExpression aritExp = null;
	
	public Factor(Identifier id) {
		this.id = id;
	}

	public Factor(ArithmeticExpression aritExp) {
		this.aritExp = aritExp;
	}

	public Factor(Numero numero) {
		this.numero = numero;
	}

	
	public Identifier getId() {
		return id;
	}

	public Numero getNumero() {
		return numero;
	}

	
	public ArithmeticExpression getAritExp() {
		return aritExp;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitFactor(this, args);
	}

}
