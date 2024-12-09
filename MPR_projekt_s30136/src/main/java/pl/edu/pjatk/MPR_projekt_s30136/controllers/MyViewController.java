package pl.edu.pjatk.MPR_projekt_s30136.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_projekt_s30136.model.Monkey;
import pl.edu.pjatk.MPR_projekt_s30136.services.MonkeyService;

@Controller
@RequestMapping("/view")
public class MyViewController {

    private final MonkeyService monkeyService;

    @Autowired
    public MyViewController(MonkeyService monkeyService) {
        this.monkeyService = monkeyService;
    }

    @GetMapping("/all")
    public String viewAllMonkeys(Model model) {
        model.addAttribute("monkeys", monkeyService.getAllMonkeys());
        return "viewAll";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("monkey", new Monkey());
        return "add";
    }

    @PostMapping("/add")
    public String addMonkey(@ModelAttribute Monkey monkey) {
        monkeyService.createMonkey(monkey);
        return "redirect:/view/all";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Integer id, Model model) {
        Monkey monkey = monkeyService.getMonkey(Long.valueOf(id));
        model.addAttribute("monkey", monkey);
        return "delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteMonkey(@PathVariable Integer id) {
        monkeyService.deleteMonkey(Long.valueOf(id));
        return "redirect:/view/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Monkey monkey = monkeyService.getMonkey(Long.valueOf(id));
        model.addAttribute("monkey", monkey);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateMonkey(@PathVariable Integer id, @ModelAttribute Monkey monkey) {
        monkeyService.updateMonkey(Long.valueOf(id), monkey);
        return "redirect:/view/all";
    }
}
