package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class ProcedureCall extends Command {
	ArrayList<Expressao> exps = new ArrayList<Expressao>();
	Identifier nome;
	
	public ProcedureCall (Identifier nome, ArrayList<Expressao> exp){
		this.nome = nome;
		this.exps = exp;
	}
	
	public Identifier getNome(){
		return this.nome;
	}
	
	public ArrayList<Expressao> getParams(){
		return this.exps;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitProcedureCall(this, args);
	}
}
