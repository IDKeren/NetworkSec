package net.dal;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import net.data.UserEntity;


public interface Crud extends ListCrudRepository<UserEntity, String>{

	 Optional<UserEntity> findByUserName(String userName);

}
