package ma.enset.hospitalapp.security.repo;

import ma.enset.hospitalapp.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String>//le id de AppRole est de type String et le type AppRole est le type de l'entité
{

}
