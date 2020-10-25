package br.com.study.theKnowledgeDungeon.service;

import br.com.study.theKnowledgeDungeon.validation.exception.TryingManipulateAnotherUserStuffException;
import br.com.study.theKnowledgeDungeon.model.User;
import br.com.study.theKnowledgeDungeon.service.onlyInterface.SessionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

	@Override
	public void testIfUserTryingManipulateAnotherUserStuff(User user) throws TryingManipulateAnotherUserStuffException {
		User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean userTryingUpdateAnotherUser = !userLogged.getId().equals(user.getId());
		if (userTryingUpdateAnotherUser)
			throw new TryingManipulateAnotherUserStuffException();
	}

}
