package keyboard.works.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import keyboard.works.entity.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, String> {

	Optional<UserRole> findByName(String name);
	
}
