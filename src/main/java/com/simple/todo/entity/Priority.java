package com.simple.todo.entity;

/**
 * Приоритет дела  от 1 до 5. Используйте getInt чтобы преобразовать enum к integer.
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
