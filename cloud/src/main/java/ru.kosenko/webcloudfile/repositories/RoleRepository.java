package ru.kosenko.webcloudfile.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kosenko.webcloudfile.entities.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
}
