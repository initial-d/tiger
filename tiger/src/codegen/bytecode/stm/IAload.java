package codegen.bytecode.stm;

import codegen.bytecode.Visitor;

public class IAload extends T{

	public IAload() {
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

}
