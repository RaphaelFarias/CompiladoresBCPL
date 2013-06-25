package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class FunctionCall extends Command {
	ArrayList<Expressao> exp = new ArrayList<Expressao>();
	Identifier nome;
	
	public FunctionCall (Identifier nome, ArrayList<Expressao> ex){
		this.nome = nome;
		this.exp = ex;
	}
	
	public Identifier getId(){
		return this.nome;
	}
	
	public ArrayList<Expressao> getParams(){
		return this.exp;
	}
	
	public ArrayList<Expressao> getExp() {
		return exp;
	}

	public Identifier getNome() {
		return nome;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitFunctionCall(this, args);
	}
}
