package pl.edu.pjatk.MPR_projekt_s30136.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Monkey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;
    private int identyfikator;

    public Monkey(String name, String color) {
        this.name = name;
        this.color = color;
        this.setIdentyfikator();
    }

    public Monkey() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
        this.setIdentyfikator();
    }

    public void setColor(String color) {
        this.color = color;
        this.setIdentyfikator();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator() {
        if (name == null || color == null) {
            this.identyfikator = 0;
            return;
        }
        int id = 0;
        char[] chars = name.toCharArray();
        char[] chars2 = color.toCharArray();
        for (char c : chars) {
            id += c;
        }
        for (char c : chars2) {
            id += c;
        }
        this.identyfikator = id;
    }
}
