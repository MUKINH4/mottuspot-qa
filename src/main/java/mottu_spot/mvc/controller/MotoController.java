package mottu_spot.mvc.controller;

import mottu_spot.mvc.model.Moto;
import mottu_spot.mvc.model.Patio;
import mottu_spot.mvc.service.MotoService;
import mottu_spot.mvc.service.PatioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;
    private final PatioService patioService;

    public MotoController(MotoService motoService, PatioService patioService) {
        this.motoService = motoService;
        this.patioService = patioService;
    }

    @GetMapping
    public String moto(@RequestParam Long patioId, Model model){
        Patio patio = patioService.encontrarPatio(patioId);
        model.addAttribute("patio", patio);
        return "moto";
    }


    @PostMapping
    public String criarMoto(@ModelAttribute Moto moto, @RequestParam Long patioId, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("errorMessage", result.getFieldError().getDefaultMessage());
            return "redirect:/adicionarMoto";
        }
        Patio patio = patioService.encontrarPatio(patioId);

        moto.setPatio(patio);

        motoService.criarMoto(moto);
        return "redirect:/patios/" + patioId;
    }

    @GetMapping("/adicionarMoto")
    public String adicionarMoto(@RequestParam Long patioId, Model model) {
        Patio patio = patioService.encontrarPatio(patioId);
        model.addAttribute("patio", patio);
        model.addAttribute("moto", new Moto());
        return "adicionarMoto";
    }

    @GetMapping("/edit/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoService.encontrarMoto(id);
        Patio patio = moto.getPatio();
        model.addAttribute("moto", moto);
        model.addAttribute("patio", patio);
        return "editarMoto";
    }

    @PostMapping("/edit/{id}")
    public String editarMoto(@PathVariable Long id, @ModelAttribute Moto moto) {
        Moto motoExistente = motoService.encontrarMoto(id);
        Patio patio = motoExistente.getPatio();
        moto.setId(id);
        moto.setPatio(patio);
        motoService.atualizarMoto(moto);
        return "redirect:/patios/" + patio.getId();
    }

    @GetMapping("/delete/{id}")
    public String deletarMoto(@PathVariable Long id) {
        Moto moto = motoService.encontrarMoto(id);
        Long patioId = moto.getPatio().getId();
        motoService.deletarMoto(id);
        return "redirect:/patios/" + patioId;
    }

}
