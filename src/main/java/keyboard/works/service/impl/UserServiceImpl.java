package keyboard.works.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import keyboard.works.entity.User;
import keyboard.works.model.request.UserRequest;
import keyboard.works.repository.UserRepository;
import keyboard.works.security.UserPrincipal;
import keyboard.works.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			throw new UsernameNotFoundException("Username " + username + " not found !");
		});
		
		List<String> authorities = user.getRoles().stream()
			.flatMap(role -> {
				return role.getAuthorities().stream();
			})
			.map(authority -> authority.getName())
			.collect(Collectors.toList());
		
		return new UserPrincipal(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public User createUser(UserRequest request) {
		
		User user  = new User();
		BeanUtils.copyProperties(request, user);
		
		user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		
		User storedUser = userRepository.save(user);
		
		return storedUser;
	}
	
}
