package com.simple.todo.entity;

/**
 * Priority of the deal (1-5 points). Use getInt to get value of enum in integer.
 */
public enum Priority {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

	private final int i;

	Priority(int i){
		this.i = i;
	}

	/**
	 *
	 * @return Value of enum in integer.
	 */
	public int getInt() {
		return i;
	}
}
