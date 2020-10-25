package br.com.study.theKnowledgeDungeon.service;

import br.com.study.theKnowledgeDungeon.security.PasswordEncrypter;
import br.com.study.theKnowledgeDungeon.validation.exception.EntityNonExistentForManipulateException;
import br.com.study.theKnowledgeDungeon.validation.exception.FormErrorException;
import br.com.study.theKnowledgeDungeon.validation.exception.TryingManipulateAnotherUserStuffException;
import br.com.study.theKnowledgeDungeon.model.User;
import br.com.study.theKnowledgeDungeon.repository.UserRepository;
import br.com.study.theKnowledgeDungeon.service.onlyInterface.SessionService;
import br.com.study.theKnowledgeDungeon.service.onlyInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final SessionService sessionService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, SessionService sessionService) {
		this.userRepository = userRepository;
		this.sessionService = sessionService;
	}

	@Override
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public User createUser(User user) throws FormErrorException {
		user.setPassword(PasswordEncrypter.getEncrypter().encode(user.getPassword()));

		testIfUserEmailAlreadyRegistered(user.getEmail());

		return userRepository.save(user);
	}

	private void testIfUserEmailAlreadyRegistered(String email) throws FormErrorException {
		Optional<User> userInDatabase = userRepository.findByEmail(email);

		boolean emailAlreadyRegistered = userInDatabase.isPresent();
		if (emailAlreadyRegistered)
			throw new FormErrorException("email", "email already registered");
	}

	@Override
	@Transactional
	public User updateUser(User user) throws TryingManipulateAnotherUserStuffException, EntityNonExistentForManipulateException {
		Optional<User> userInDatabase = userRepository.findById(user.getId());
		if (!userInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		sessionService.testIfUserTryingManipulateAnotherUserStuff(user);

		User userToUpdate = userInDatabase.get();

		userToUpdate.setName(user.getName());
		userToUpdate.setPassword(PasswordEncrypter.getEncrypter().encode(user.getPassword()));

		return userRepository.save(userToUpdate);
	}

	@Override
	public void deleteUser(Long id) throws EntityNonExistentForManipulateException, TryingManipulateAnotherUserStuffException {
		Optional<User> userInDatabase = userRepository.findById(id);
		if (!userInDatabase.isPresent())
			throw new EntityNonExistentForManipulateException();

		sessionService.testIfUserTryingManipulateAnotherUserStuff(userInDatabase.get());

		userRepository.deleteById(id);
	}

}
