package br.com.study.theKnowledgeDungeon.service.onlyInterface;

import br.com.study.theKnowledgeDungeon.validation.exception.TryingManipulateAnotherUserStuffException;
import br.com.study.theKnowledgeDungeon.model.User;

public interface SessionService {

	void testIfUserTryingManipulateAnotherUserStuff(User user) throws TryingManipulateAnotherUserStuffException;

}
