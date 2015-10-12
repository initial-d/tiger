package codegen.bytecode.stm;

import codegen.bytecode.Visitor;

public class IAstore extends T {

	public int index;

	public IAstore(int index) {
		this.index = index;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}


}
