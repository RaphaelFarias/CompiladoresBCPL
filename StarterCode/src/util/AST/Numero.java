package util.AST;

import checker.SemanticException;

public class Numero extends Terminal {

	public Numero(String spelling) {
		super(spelling);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitNumero(this, args);
	}

}
