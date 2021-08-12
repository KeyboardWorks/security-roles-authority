package keyboard.works.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

	@PreAuthorize("hasAuthority('READ_HOME')")
	@GetMapping
	public String getHome() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		return "get home " + authentication.getPrincipal();
	}
	
	@PreAuthorize("hasAuthority('CREATE_HOME')")
	@PostMapping
	public String postHome() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		return "post home " + authentication.getPrincipal();
	}
	
	@PreAuthorize("hasAuthority('UPDATE_HOME')")
	@PutMapping
	public String putHome() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		return "put home " + authentication.getPrincipal();
	}
	
	@PreAuthorize("hasAuthority('DELETE_HOME')")
	@DeleteMapping
	public String deleteHome() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		return "delete home " + authentication.getPrincipal();
	}
	
}
