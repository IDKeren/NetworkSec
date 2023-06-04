package net.dal;

import org.springframework.data.repository.ListCrudRepository;
import net.data.CustomerEntity;

public interface CustomerCrud extends ListCrudRepository<CustomerEntity, String>{


}
