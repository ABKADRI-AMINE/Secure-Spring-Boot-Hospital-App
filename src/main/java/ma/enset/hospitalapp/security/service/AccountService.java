package ma.enset.hospitalapp.security.service;

import ma.enset.hospitalapp.security.entities.AppRole;
import ma.enset.hospitalapp.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password,String email,String confirmPassword);//pour ajouter un utilisateur dans la base de données et retourner l'utilisateur ajouté
    AppRole addNewRole(String role);//pour ajouter un role dans la base de données et retourner le role ajouté
    void addRoleToUser(String username, String role);//pour ajouter un role à un utilisateur
    void removeRoleToUser(String username, String role);//pour supprimer un role à un utilisateur
    AppUser loadUserByUsername(String username);//pour charger un utilisateur par son username
}
