package ma.ensaf.springbootjpa.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ma.ensaf.springbootjpa.model.Medicament;
import ma.ensaf.springbootjpa.model.Role;
import ma.ensaf.springbootjpa.model.User;
import ma.ensaf.springbootjpa.repository.MedicamentRepository;
import ma.ensaf.springbootjpa.repository.RoleRepository;
import ma.ensaf.springbootjpa.repository.UserRepository;

@Component
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MedicamentRepository medicamentRepository;
    private final PasswordEncoder passwordEncoder;

    public InitData(UserRepository userRepository,
                    RoleRepository roleRepository,
                    MedicamentRepository medicamentRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.medicamentRepository = medicamentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role admin = creerRole("ADMIN");
        Role pharmacien = creerRole("PHARMACIEN");
        Role caissier = creerRole("CAISSIER");
        creerRole("CLIENT");

        creerUtilisateur("admin", "admin123", admin);
        creerUtilisateur("pharma", "pharma123", pharmacien);
        creerUtilisateur("caisse", "caisse123", caissier);

        if (medicamentRepository.count() == 0) {
            medicamentRepository.save(new Medicament("Doliprane 500mg", "Antalgique et antipyrétique.",
                    new BigDecimal("25.50"), 100, LocalDate.of(2027, 5, 10)));
            medicamentRepository.save(new Medicament("Amoxicilline 500mg", "Antibiotique à large spectre.",
                    new BigDecimal("45.00"), 50, LocalDate.of(2027, 3, 10)));
            medicamentRepository.save(new Medicament("Vitamine C 1000mg", "Complément pour renforcer les défenses.",
                    new BigDecimal("30.00"), 80, LocalDate.of(2027, 6, 15)));
            medicamentRepository.save(new Medicament("Bétadine Solution 10%", "Désinfectant pour usage externe.",
                    new BigDecimal("18.00"), 40, LocalDate.of(2028, 1, 30)));
        }
    }

    private Role creerRole(String nom) {
        return roleRepository.findByName(nom)
                .orElseGet(() -> roleRepository.save(new Role(nom)));
    }

    private void creerUtilisateur(String username, String motDePasse, Role role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(motDePasse));
            user.setEnabled(true);
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }
}