package br.com.study.theKnowledgeDungeon.service.onlyInterface;

import br.com.study.theKnowledgeDungeon.validation.exception.EntityNonExistentForManipulateException;
import br.com.study.theKnowledgeDungeon.validation.exception.FormErrorException;
import br.com.study.theKnowledgeDungeon.validation.exception.TryingManipulateAnotherUserStuffException;
import br.com.study.theKnowledgeDungeon.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

	Page<User> getUsers(Pageable pageable);

	Optional<User> getUser(Long id);

	User createUser(User user) throws FormErrorException;

	User updateUser(User user) throws TryingManipulateAnotherUserStuffException, EntityNonExistentForManipulateException;

	void deleteUser(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException;

}
