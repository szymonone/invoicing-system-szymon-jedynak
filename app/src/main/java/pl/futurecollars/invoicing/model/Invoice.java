package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {

    private int id;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;
}


