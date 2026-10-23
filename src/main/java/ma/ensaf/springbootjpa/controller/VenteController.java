package ma.ensaf.springbootjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ma.ensaf.springbootjpa.model.Medicament;
import ma.ensaf.springbootjpa.repository.MedicamentRepository;

@Controller
@RequestMapping("/ventes")
public class VenteController {

    private final MedicamentRepository medicamentRepository;

    public VenteController(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @GetMapping
    public String formulaire(Model model) {
        model.addAttribute("medicaments", medicamentRepository.findAll());
        return "ventes";
    }

    @PostMapping
    public String vendre(@RequestParam Long medicamentId,
                         @RequestParam int quantite,
                         RedirectAttributes redirect) {
        Medicament m = medicamentRepository.findById(medicamentId).orElse(null);

        if (m == null) {
            redirect.addFlashAttribute("erreur", "Médicament introuvable.");
        } else if (quantite <= 0) {
            redirect.addFlashAttribute("erreur", "La quantité doit être supérieure à 0.");
        } else if (quantite > m.getQuantite()) {
            redirect.addFlashAttribute("erreur",
                    "Stock insuffisant : il reste " + m.getQuantite() + " unité(s) de " + m.getNom() + ".");
        } else {
            m.setQuantite(m.getQuantite() - quantite);
            medicamentRepository.save(m);
            redirect.addFlashAttribute("succes",
                    "Vente enregistrée : " + quantite + " x " + m.getNom()
                    + " (" + m.getPrix().multiply(java.math.BigDecimal.valueOf(quantite)) + " DH).");
        }
        return "redirect:/ventes";
    }
}