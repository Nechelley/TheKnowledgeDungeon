package br.com.study.theKnowledgeDungeon.controller.dto.in;

import br.com.study.theKnowledgeDungeon.controller.dto.group.OnCreate;
import br.com.study.theKnowledgeDungeon.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserInDto {

	@NotEmpty
	@Length(min = 5, max = 100)
	private String name;
	@NotEmpty
	@Length(min = 10, max = 500)
	private String password;
	@NotEmpty(groups = OnCreate.class)
	@Length(min = 10, max = 100, groups = OnCreate.class)
	@Email
	private String email;

	public User createUser() {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

}
