package br.com.study.theKnowledgeDungeon.validation.handle;

import br.com.study.theKnowledgeDungeon.validation.exception.TryingManipulateAnotherUserStuffException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TryingManipulateAnotherUserStuffExceptionHandler {

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TryingManipulateAnotherUserStuffException.class)
	public ResponseEntity<Object> handle() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}