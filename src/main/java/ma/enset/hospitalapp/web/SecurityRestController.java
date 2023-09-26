package ma.enset.hospitalapp.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//pour dire que c'est un controller qui va retourner des données au format JSON et non pas des vues (pages html)
public class SecurityRestController {
    @GetMapping("/profile")
    public Authentication authentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
