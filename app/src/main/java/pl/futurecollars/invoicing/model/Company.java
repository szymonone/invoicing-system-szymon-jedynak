package pl.futurecollars.invoicing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {

    private String taxIdentificationNumber;
    private String address;
    private String name;
}

