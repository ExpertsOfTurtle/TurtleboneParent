package com.turtlebone.contract.common;

public interface IContractStatus {
	public static final short PENDING = 0;
	public static final short SIGNED = 1;
	public static final short COMPLETE = 2;
	public static final short CANCEL = -1;
	public static final short REJECTED = -2;
}
