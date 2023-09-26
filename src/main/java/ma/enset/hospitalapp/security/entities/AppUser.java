package ma.enset.hospitalapp.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder//pour construire un objet de type AppUser avec le design pattern builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
//    @ManyToMany(fetch = FetchType.LAZY  )//pour ne pas charger les roles avec l'utilisateur (lazy loading) en memoire mais il les va les charger que uniquement quand on va les utiliser
    @ManyToMany(fetch = FetchType.EAGER  )//EN MEME Temps il charge tous les attribut ainsi que les roles de l'utilisateur
    private List<AppRole> roles;//pour dire que chaque utilisateur peut avoir plusieurs roles et chaque role peut avoir plusieurs utilisateurs (relation many to many)
}
