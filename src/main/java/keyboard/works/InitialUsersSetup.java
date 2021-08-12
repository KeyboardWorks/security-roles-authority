package keyboard.works;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import keyboard.works.entity.User;
import keyboard.works.entity.UserAuthority;
import keyboard.works.entity.UserRole;
import keyboard.works.model.request.UserRequest;
import keyboard.works.repository.UserAuthorityRepository;
import keyboard.works.repository.UserRepository;
import keyboard.works.repository.UserRoleRepository;
import keyboard.works.service.UserService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InitialUsersSetup {

	private UserService userService;
	
	private UserRepository userRepository;
	
	private UserRoleRepository userRoleRepository;
	
	private UserAuthorityRepository userAuthorityRepository;
	
	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		UserAuthority readHomeAuthority = createUserAuthority("READ_HOME");
		UserAuthority createHomeAuthority = createUserAuthority("CREATE_HOME");
		UserAuthority updateHomeAuthority = createUserAuthority("UPDATE_HOME");
		UserAuthority deleteHomeAuthority = createUserAuthority("DELETE_HOME");
		
		UserRole adminRole = createUserRole("ROLE_ADMIN", Arrays.asList(readHomeAuthority, createHomeAuthority, updateHomeAuthority, deleteHomeAuthority));
		UserRole staffRole = createUserRole("ROLE_STAFF", Arrays.asList(readHomeAuthority, updateHomeAuthority));
		
		User adminUser = createAdminUser();
		adminUser.setRoles(new HashSet<>());
		adminUser.getRoles().add(adminRole);
		
		userRepository.save(adminUser);
		
		User staffUser = createStaffUser();
		staffUser.setRoles(new HashSet<>());
		staffUser.getRoles().add(staffRole);
		
		userRepository.save(staffUser);
	}
	
	private User createAdminUser() {
		
		UserRequest request = new UserRequest();
		request.setName("Admin");
		request.setUsername("admin");
		request.setPassword("123123");
		
		return userService.createUser(request);
	}
	
	private User createStaffUser() {
		
		UserRequest request = new UserRequest();
		request.setName("Staff");
		request.setUsername("staff");
		request.setPassword("123123");
		
		return userService.createUser(request);
	}
	
	private UserRole createUserRole(String name, List<UserAuthority> authorities) {
		
		UserRole userRole = userRoleRepository.findByName(name).orElse(new UserRole(name));
		userRole.setAuthorities(new HashSet<>());
		userRole.getAuthorities().addAll(authorities);
		
		UserRole storedUserRole = userRoleRepository.save(userRole);
		
		return storedUserRole;
	}
	
	private UserAuthority createUserAuthority(String name) {
		
		UserAuthority userAuthority = userAuthorityRepository.findByName(name).orElse(new UserAuthority(name));
		
		UserAuthority storedUserAuthority = userAuthorityRepository.save(userAuthority);
		
		return storedUserAuthority;
	}
	
}
