package ma.ensaf.springbootjpa.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ma.ensaf.springbootjpa.model.Medicament;
import ma.ensaf.springbootjpa.repository.MedicamentRepository;

@Controller
@RequestMapping("/medicaments")
public class MedicamentController {

    private final MedicamentRepository medicamentRepository;

    public MedicamentController(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @GetMapping
    public String liste(Authentication authentication, Model model) {
        boolean admin = aRole(authentication, "ROLE_ADMIN");
        boolean pharmacien = aRole(authentication, "ROLE_PHARMACIEN");
        model.addAttribute("medicaments", medicamentRepository.findAll());
        model.addAttribute("admin", admin);
        model.addAttribute("peutModifier", admin || pharmacien);
        return "medicaments";
    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("medicament", new Medicament());
        return "medicament-form";
    }

    @GetMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, Model model) {
        return medicamentRepository.findById(id)
                .map(m -> {
                    model.addAttribute("medicament", m);
                    return "medicament-form";
                })
                .orElse("redirect:/medicaments");
    }

    @PostMapping
    public String enregistrer(@ModelAttribute Medicament medicament) {
        medicamentRepository.save(medicament);
        return "redirect:/medicaments";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        medicamentRepository.deleteById(id);
        return "redirect:/medicaments";
    }

    private boolean aRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}