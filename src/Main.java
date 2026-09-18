void main() {
    Fahrzeug auto_1 = new Fahrzeug(0, "WBAQ542", true, "rot", "BYD", 5);
    auto_1.incrementKilometerstand(100);
    auto_1.getInfo("kms");

    Fuhrpark garage = new Fuhrpark();
    garage.addCar(auto_1);
    garage.getCar("WBAQ542").getInfo("farb");
}
