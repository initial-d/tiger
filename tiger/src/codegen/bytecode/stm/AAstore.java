package codegen.bytecode.stm;

import codegen.bytecode.Visitor;

public class AAstore extends T {

	public int index;

	public AAstore(int index) {
		this.index = index;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
