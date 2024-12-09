package pl.edu.pjatk.MPR_projekt_s30136.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_projekt_s30136.exceptions.ResourceNotFoundException;
import pl.edu.pjatk.MPR_projekt_s30136.model.Monkey;
import pl.edu.pjatk.MPR_projekt_s30136.repository.MonkeyRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonkeyService {

    private final MonkeyRepository monkeyRepository;
    private final StringUtilsService stringUtilsService;

    @Autowired
    public MonkeyService(MonkeyRepository repository, StringUtilsService stringUtilsService) {
        this.monkeyRepository = repository;
        this.stringUtilsService = stringUtilsService;
        this.monkeyRepository.save(new Monkey("Wladziu", "gray"));
        this.monkeyRepository.save(new Monkey("Damian", "green"));
        this.monkeyRepository.save(new Monkey("Kuba", "blue"));
    }

    public void addMonkey(Monkey monkey) {
        monkey.setName(stringUtilsService.toUpperCase(monkey.getName()));
        monkey.setColor(stringUtilsService.toUpperCase(monkey.getColor()));
        this.monkeyRepository.save(monkey);
    }

    public List<Monkey> getAllMonkeys() {
        List<Monkey> monkeys = new ArrayList<>();
        monkeyRepository.findAll().forEach(monkey -> {
            monkey.setName(stringUtilsService.toCapitalized(monkey.getName()));
            monkey.setColor(stringUtilsService.toCapitalized(monkey.getColor()));
            monkeys.add(monkey);
        });
        return monkeys;
    }

    public Monkey getMonkey(Long id) {
        Optional<Monkey> monkey = monkeyRepository.findById(id);
        if (monkey.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Monkey m = monkey.get();
        m.setName(stringUtilsService.toCapitalized(m.getName()));
        m.setColor(stringUtilsService.toCapitalized(m.getColor()));
        return m;
    }

    public boolean deleteMonkey(Long id) {
        if (!monkeyRepository.existsById(id)) {
            return false;
        }
        monkeyRepository.deleteById(id);
        return true;
    }

    public List<Monkey> getbyName(String name) {
        return monkeyRepository.findByName(name);
    }

    public Monkey createMonkey(Monkey monkey) {
        monkey.setName(stringUtilsService.toUpperCase(monkey.getName()));
        monkey.setColor(stringUtilsService.toUpperCase(monkey.getColor()));
        return monkeyRepository.save(monkey);
    }

    public Optional<Monkey> updateMonkey(Long id, Monkey monkey) {
        return monkeyRepository.findById(id).map(existingMonkey -> {
            existingMonkey.setName(stringUtilsService.toUpperCase(monkey.getName()));
            existingMonkey.setColor(stringUtilsService.toUpperCase(monkey.getColor()));
            return monkeyRepository.save(existingMonkey);
        });
    }

    public byte[] generatePdf(Monkey monkey) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Monkey Details:");
                contentStream.newLine();
                contentStream.showText("ID: " + monkey.getId());
                contentStream.newLine();
                contentStream.showText("Name: " + monkey.getName());
                contentStream.newLine();
                contentStream.showText("Color: " + monkey.getColor());
                contentStream.newLine();
                contentStream.endText();

            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }
}
