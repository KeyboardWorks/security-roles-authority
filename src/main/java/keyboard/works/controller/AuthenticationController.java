package keyboard.works.controller;

import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import keyboard.works.SpringAppContext;
import keyboard.works.model.request.LoginRequest;
import keyboard.works.model.response.LoginResponse;
import keyboard.works.model.response.UserResponse;
import keyboard.works.utils.JwtHelper;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public LoginResponse userLogin(@RequestBody LoginRequest request) throws JsonProcessingException {
		
		LoginResponse response = new LoginResponse();
		UserResponse userResponse = new UserResponse();
		ObjectMapper mapper = SpringAppContext.getBean(ObjectMapper.class);
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		userResponse.setUsername(request.getUsername());
		userResponse.setAuthorities(authenticate.getAuthorities().stream()
				.map(authority -> {
					return authority.getAuthority();
				})
				.collect(Collectors.toSet()));
		
		String token = JwtHelper.createToken(mapper.writeValueAsString(userResponse));
		response.setToken(token);
		
		return response;
	}
	
}
