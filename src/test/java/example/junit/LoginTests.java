package example.junit;

import internal.Medarbejder;
import internal.Projekt;
import internal.SystemApp;
import internal.SystemAppException;
import org.junit.Assert;

public class LoginTests {
    SystemApp system;

    @org.junit.Before()
    public void testConstructor() throws SystemAppException {
        system = new SystemApp();
        system.fåMedarbejdere().add(new Medarbejder("MEB"));
        Medarbejder m = system.findMedarbejder("MEB");
        system.fåProjekter().add(new Projekt("Projekt 1", "2023-01"));
        Projekt p = system.findProjektMedID("2023-01");
        system.tilføjMedarbejderTilProjekt(m, p);
    }

    @org.junit.Test()
    public void testLoginAdmin() {
        system.login("admin");
        Assert.assertTrue(system.isAdmin());
        Assert.assertTrue(system.isLoggedIn());

        // Remove medarbejder
        system.sletMedarbejder(system.findMedarbejder("MEB"));
        Assert.assertTrue(system.fåMedarbejdere().isEmpty());

        system.logout();
        Assert.assertFalse(system.isAdmin());
        Assert.assertFalse(system.isLoggedIn());
    }

    @org.junit.Test()
    public void testLoginMedarbejder() {
        system.login("MEB");
        Assert.assertFalse(system.isAdmin());
        Assert.assertTrue(system.isLoggedIn());
        system.logout();
        Assert.assertFalse(system.isAdmin());
        Assert.assertFalse(system.isLoggedIn());
    }

    @org.junit.Test()
    public void testLoginNull() {
        Assert.assertFalse(system.login("yeyo"));
    }
}
