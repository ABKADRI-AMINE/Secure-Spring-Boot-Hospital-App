package ma.enset.hospitalapp.security.service;

import lombok.AllArgsConstructor;
import ma.enset.hospitalapp.security.entities.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//pour charger l'utilisateur par son username
        AppUser appUser = accountService.loadUserByUsername(username);//pour charger l'utilisateur par son username
        if(appUser==null) throw new UsernameNotFoundException(String.format("User with username %s not found",username));//si l'utilisateur n'existe pas on va lancer une exception
// String[] roles=appUser.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);//pour récupérer les roles de l'utilisateur et les mettre dans un tableau de type String (String[]) et le retourner
      List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream().map(r->new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());//pour récupérer les authoritie de l'utilisateur et les mettre dans une liste de type SimpleGrantedAuthority et le retourner
        UserDetails userDetails= User//pour créer un utilisateur de type UserDetails
                .withUsername(appUser.getUsername())//pour spécifier le username de l'utilisateur
                .password(appUser.getPassword())//pour spécifier le password de l'utilisateur
//                .roles(roles)//pour spécifier les roles de l'utilisateur
                .authorities(authorities)   //pour spécifier les authoritie de l'utilisateur
                .build();//pour construire l'utilisateur
return userDetails;
    }
}
