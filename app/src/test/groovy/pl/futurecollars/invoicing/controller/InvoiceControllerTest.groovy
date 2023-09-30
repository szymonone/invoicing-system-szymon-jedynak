package pl.futurecollars.invoicing.controller

import org.objectweb.asm.TypeReference
import org.spockframework.mock.MockImplementation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.futurecollars.invoicing.TestHelper
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.service.InvoiceService
import pl.futurecollars.invoicing.service.JsonService
import spock.lang.Specification

import java.time.LocalDate

@AutoConfigureMockMvc
@SpringBootTest
class InvoiceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    @Autowired
    private InvoiceService invoiceService

    def "Should return empty array when no invoices were created"() {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/invoice"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        result == "[]"
    }

    def "Should save invoice to id 1"(){
        given:
        def invoice = TestHelper.invoice(1)
        def invoiceJson = jsonService.objectToJson(invoice)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/invoice")
                .content(invoiceJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .response
                .contentAsString
        then:
        Integer.valueOf(result) == 1
        invoiceService.getById(1).isPresent()
    }

    def "Should return all invoices"(){
        given:
        def invoice = TestHelper.invoice(1)
        invoiceService.save(invoice)

        when:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.get("/invoice")
                    .content()
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        def expectedJson = jsonService.jsonToObject(resultJson, Invoice[])
        expectedJson == invoiceService.getAlL()
    }

    def "Should return invoice from the id 1"() {
        given:
        def invoice = TestHelper.invoice(1)
        invoiceService.save(invoice)

        when:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.get("/invoice/1")
                    .content()
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        def result = jsonService.jsonToObject(resultJson, Invoice)
        Optional.of(result) == invoiceService.getById(1)
    }

    def "Shouldn't get invoice because id 1000 non exist - response 404"(){
        expect:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.get("/invoice/1000"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
    }

    def "Should update invoice id 1"() {
        given:
        def invoice = TestHelper.invoice(1)
        def actualDate = LocalDate.now().plusDays(10)
        invoice.setDate(actualDate)
        def invoiceJson = jsonService.objectToJson(invoice)
        invoiceService.save(invoice)

        when:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.put("/invoice/1")
                    .content(invoiceJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString
        then:
        def result = jsonService.jsonToObject(resultJson, Invoice)
        result.getDate() == actualDate
    }

    def "Shouldn't update invoice because id 1000 non exist - response 404"() {
        expect:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.put("/invoice/1000"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
    }

    def "Should delete invoice from id 1"() {
        given:
        def invoice = TestHelper.invoice(1)
        invoiceService.save(invoice)

        when:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.delete("/invoice/1")
                    .content()
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        invoiceService.getById(1).isEmpty()
    }

    def "Shouldn't delete invoice because id 1000 non exist - response 404"() {
        expect:
        def resultJson = mockMvc.perform(MockMvcRequestBuilders.delete("/invoice/100"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
    }
}
