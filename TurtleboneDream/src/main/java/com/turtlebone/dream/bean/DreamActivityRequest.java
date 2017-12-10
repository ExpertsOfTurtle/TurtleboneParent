package com.turtlebone.dream.bean;

import com.turtlebone.core.enums.sudoku.SudokuLevel;

import lombok.Data;

@Data
public class DreamActivityRequest {
	private String username;
	private String datetime;
	private String content;
	private String dreampic;
	private Integer dreamId;
}
