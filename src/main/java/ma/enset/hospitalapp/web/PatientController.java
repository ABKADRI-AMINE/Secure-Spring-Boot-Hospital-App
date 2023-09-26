package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping("/user/index")  // Annotation indiquant que cette méthode est invoquée par une requête HTTP GET sur l'URL "/index"
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        // Récupère la liste des patients en fonction des paramètres fournis (kw, page, size)
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page, size));

        // Ajoute la liste des patients à l'objet Model pour être utilisée dans la vue
        model.addAttribute("listPatients", pagePatients.getContent());

        // Ajoute un tableau d'entiers représentant les numéros de pages à l'objet Model
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);

        // Ajoute le numéro de la page actuelle à l'objet Model
        model.addAttribute("currentPage", page);

        // Ajoute le mot-clé (keyword) utilisé pour la recherche à l'objet Model
        model.addAttribute("keyword", kw);

        // Retourne le nom de la vue "patients" à afficher (généralement un fichier HTML)
        return "patients";
    }

    @GetMapping("/admin/deletePatient")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePatient(@RequestParam(name = "id") Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String formPatient(Model model ){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }
    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "formPatient";
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasAuthority('ADMIN')")

    public String editPatient(@RequestParam(name = "id") Long id, Model model){
        Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}
