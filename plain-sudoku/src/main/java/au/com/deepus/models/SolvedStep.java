package au.com.deepus.models;

import lombok.Data;

@Data
public class SolvedStep {

    public SolvedStep(String description) {
        this.description = description;
    }

    private String description;
}
