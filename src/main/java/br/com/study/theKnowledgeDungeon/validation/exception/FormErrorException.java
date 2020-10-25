package br.com.study.theKnowledgeDungeon.validation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormErrorException extends Exception {

	private String field;
	private String error;

}
