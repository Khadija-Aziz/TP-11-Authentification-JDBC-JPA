package ma.fstg.security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        // On récupère toutes les autorités (rôles) de l'utilisateur connecté
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // On vérifie la présence du rôle avec le préfixe complet "ROLE_"
        // car c'est ainsi qu'ils sont stockés par mon CustomUserDetailsService
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            return "redirect:/admin/dashboard";
        } else if (isUser) {
            return "redirect:/user/dashboard";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user-dashboard";
    }
}