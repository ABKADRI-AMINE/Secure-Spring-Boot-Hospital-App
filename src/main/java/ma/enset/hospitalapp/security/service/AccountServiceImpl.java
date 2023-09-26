package ma.enset.hospitalapp.security.service;

import lombok.AllArgsConstructor;
import ma.enset.hospitalapp.security.entities.AppRole;
import ma.enset.hospitalapp.security.entities.AppUser;
import ma.enset.hospitalapp.security.repo.AppRoleRepository;
import ma.enset.hospitalapp.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser= appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
         appUser = AppUser.builder()//pour créer un utilisateur et l'ajouter dans la base de données et retourner l'utilisateur ajouté
                .userId(UUID.randomUUID().toString())//pour générer un id unique pour chaque utilisateur
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();//pour construire l'utilisateur
         AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
AppRole appRole = appRoleRepository.findById(role).orElse(null);//pour vérifier si le role existe ou pas
if(appRole!=null) throw new RuntimeException("Role already exists");//si le role existe on va lancer une exception
appRole = AppRole.builder()//pour créer un role et l'ajouter dans la base de données et retourner le role ajouté
        .role(role)//pour construire le role
        .build();//pour construire le role
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
AppUser appUser = appUserRepository.findByUsername(username);
AppRole appRole = appRoleRepository.findById(role).get();
appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);

    }
    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);//pour charger un utilisateur par son username
    }
}
