package dev.fullslack.niklas.xmlgenerator;

import dev.fullslack.niklas.xmlgenerator.model.Companies;
import dev.fullslack.niklas.xmlgenerator.model.Company;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.util.ArrayList;

public class SimpleGeneratorApplication {

    private static Companies companies = new Companies();

    static {
        companies.setCompanies(new ArrayList<Company>());
        // Create two companies
        Company comp1 = new Company();
        comp1.setId(1);
        comp1.setName("Coca-Cola Nederland");
        comp1.setAddress("Marten Meesweg 25");
        comp1.setZipcode("3068AV");
        comp1.setCity("Rotterdam");
        comp1.setCountry("Netherlands");

        Company comp2 = new Company();
        comp2.setId(2);
        comp2.setName("Bavaria N.V.");
        comp2.setAddress("De Stater 1");
        comp2.setZipcode("5737RV");
        comp2.setCity("Lieshout");
        comp2.setCountry("Netherlands");

        // Add the companies in list
        companies.getCompanies().add(comp1);
        companies.getCompanies().add(comp2);
    }

    public static void main(String[] args) {
        try {
            marshalingExample();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void marshalingExample() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Companies.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the companies list in console
        jaxbMarshaller.marshal(companies, System.out);

        // Marshal the companies list to file
        jaxbMarshaller.marshal(companies, new File("D:/Java/companies.xml"));
    }
}
