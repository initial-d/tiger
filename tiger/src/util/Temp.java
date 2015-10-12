package util;

public class Temp {

	private int i;
	private static int count = 0;

	public Temp() {
		i = count++;
	}

	// Factory pattern
	public String toString() {
		return "x_" + (Integer.toString(this.i));
	}

	public static String next() {
		return "x_" + (Temp.count++);
	}
}
