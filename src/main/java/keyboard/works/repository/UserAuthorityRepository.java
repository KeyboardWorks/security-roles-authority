package keyboard.works.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.UserAuthority;

@Repository
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, String> {

	Optional<UserAuthority> findByName(String name);
	
}
