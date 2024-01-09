package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {
    private Double pricePerDay;
    private Double pricePerHour;
    private TaxService taxServices;

    public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxServices) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxServices = taxServices;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public TaxService getTaxServices() {
        return taxServices;
    }

    public void setTaxServices(BrazilTaxServices taxServices) {
        this.taxServices = taxServices;
    }
    public void processInvoice(CarRental carRental){
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60.0;

        double basicPayment;
        if (hours <= 12.0){
            basicPayment = pricePerHour * Math.ceil(hours);
        }
        else {
            basicPayment = pricePerDay * Math.ceil(hours / 24);
        }

        double tax  = taxServices.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));

    }
}
