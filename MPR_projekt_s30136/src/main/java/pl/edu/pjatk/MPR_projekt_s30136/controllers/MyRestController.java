package pl.edu.pjatk.MPR_projekt_s30136.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_projekt_s30136.model.Monkey;
import pl.edu.pjatk.MPR_projekt_s30136.services.MonkeyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monkey")
public class MyRestController {

    private final MonkeyService monkeyService;

    @Autowired
    public MyRestController(MonkeyService monkeyService) {
        this.monkeyService = monkeyService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Monkey>> getMonkeyByName(@PathVariable String name) {
        List<Monkey> monkeys = this.monkeyService.getbyName(name);
        return monkeys.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(monkeys, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Monkey>> getAll() {
        List<Monkey> monkeys = this.monkeyService.getAllMonkeys();
        return new ResponseEntity<>(monkeys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monkey> get(@PathVariable Long id) {
        try {
            Monkey monkey = this.monkeyService.getMonkey(id);
            return new ResponseEntity<>(monkey, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Monkey> create(@RequestBody Monkey monkey) {
        Monkey createdMonkey = this.monkeyService.createMonkey(monkey);
        return new ResponseEntity<>(createdMonkey, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monkey> update(@PathVariable Long id, @RequestBody Monkey monkey) {
        Optional<Monkey> updatedMonkey = this.monkeyService.updateMonkey(id, monkey);
        return updatedMonkey.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = this.monkeyService.deleteMonkey(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getMonkeyAsPdf(@PathVariable Long id) {
        try {
            Monkey monkey = this.monkeyService.getMonkey(id);
            byte[] pdfContent = this.monkeyService.generatePdf(monkey);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=monkey_" + id + ".pdf")
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
