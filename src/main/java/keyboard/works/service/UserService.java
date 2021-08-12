package keyboard.works.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import keyboard.works.entity.User;
import keyboard.works.model.request.UserRequest;

public interface UserService extends UserDetailsService {

	User createUser(UserRequest request);
	
}
