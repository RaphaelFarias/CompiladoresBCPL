package util.AST;

import checker.SemanticException;

public class Identifier extends Terminal {
	public int type = 0; // 0 para nome de variavel, 1 para funcao, 2 para procedimento, 3 parametro e 4 string
	public boolean local = true; //true indica variavel local, false indica variavel global
	public Object declaration;
	public String tipo;
	public int memoryPosition;
		
	public Identifier(String spelling) { 
		super(spelling); 
	}
	
	public String getType(){
		String type = "";
		if(this.type == 0){
			type = "variavel";
		}else if(this.type == 1){
			type = "funcao";
		}else if(this.type == 2){
			type =  "procedimento";
		}else if(this.type == 3){
			type = "parametro";
		}
		return type;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitIdentifier(this, args);
	}
}
