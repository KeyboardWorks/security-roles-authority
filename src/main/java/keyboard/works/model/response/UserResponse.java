package keyboard.works.model.response;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private String username;
	
	private Set<String> authorities;
	
}
