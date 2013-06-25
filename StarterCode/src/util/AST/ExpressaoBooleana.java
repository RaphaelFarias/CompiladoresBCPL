package util.AST;

import checker.SemanticException;

public class ExpressaoBooleana extends Expressao {
	ComparacaoBooleana compBool = null;
	ComparacaoNumero compNum = null;
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	public ExpressaoBooleana(ComparacaoBooleana compBoll) {
		super();
		this.compBool = compBoll;
	}
	public ExpressaoBooleana(ComparacaoNumero compNum) {
		super();
		this.compNum = compNum;
	}
	
	public ComparacaoBooleana getCompBool() {
		return compBool;
	}
	public ComparacaoNumero getCompNum() {
		return compNum;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitExpressaoBooleana(this, args);
	}
}

