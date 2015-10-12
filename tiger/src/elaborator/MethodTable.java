package elaborator;

import java.util.Iterator;

import util.Todo;

public class MethodTable {
	private java.util.Hashtable<String, ast.type.T> table;

	public MethodTable() {
		this.table = new java.util.Hashtable<String, ast.type.T>();
	}

	// Duplication is not allowed
	public void put(java.util.LinkedList<ast.dec.T> formals,
			java.util.LinkedList<ast.dec.T> locals) {
		for (ast.dec.T dec : formals) {
			ast.dec.Dec decc = (ast.dec.Dec) dec;
			if (this.table.get(decc.id) != null) {
				System.out.println("line :  "+ decc.lineNo +"  duplicated parameter: " + decc.id);
			//	System.exit(1);
			}
			this.table.put(decc.id, decc.type);
		}

		for (ast.dec.T dec : locals) {
			ast.dec.Dec decc = (ast.dec.Dec) dec;
			if (this.table.get(decc.id) != null) {
				System.out.println("duplicated variable: " + decc.id);
			//	System.exit(1);
			}
			this.table.put(decc.id, decc.type);
		}

	}

	// return null for non-existing keys
	public ast.type.T get(String id) {
		return this.table.get(id);
	}

	public void dump() {
		//
		String key;
		for (Iterator it = table.keySet().iterator(); it.hasNext();) {
			key =  (String) it.next();
			ast.type.T value = table.get(key);
			System.out.print(key + "--");
			System.out.println(value);
		}
	}
	public void clear() {
		//
		table.clear();
	}
	@Override
	public String toString() {
		return this.table.toString();
	}
}
