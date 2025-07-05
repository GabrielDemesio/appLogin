package br.appLogin.br.appLogin.repositories;

import br.appLogin.br.appLogin.models.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
    UserModel findById(Long id);

    @Query(value = "select * from appLogin.usuarios where email = :email and senha = :senha", nativeQuery = true);
    public UserModel login(String email, String senha);
}
