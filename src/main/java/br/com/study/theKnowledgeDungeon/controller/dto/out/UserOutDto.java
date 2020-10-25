package br.com.study.theKnowledgeDungeon.controller.dto.out;

import br.com.study.theKnowledgeDungeon.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class UserOutDto {

	private Long id;
	private String name;
	private String email;

	public UserOutDto(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
	}

	public static Page<UserOutDto> createDtoFromUsersList(Page<User> users) {
		return users.map(UserOutDto::new);
	}

}
