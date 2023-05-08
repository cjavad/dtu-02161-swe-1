package example.junit.whitebox;

import internal.Medarbejder;
import internal.Projekt;
import internal.SystemApp;
import internal.SystemAppException;

public class FjernProjektTests {
    SystemApp systemApp;
    Medarbejder m;
    Projekt projekt;

    @org.junit.Before()
    public void setup() throws SystemAppException {
        systemApp = new SystemApp();
        systemApp.login("admin");
        m = new Medarbejder("aaaa");
        systemApp.lavNytProjekt("Projekt 1");
        projekt = systemApp.findProjektMedNavn("Projekt 1");
        systemApp.tilføjMedarbejderTilProjekt(m, projekt);
        systemApp.lavAktivitet("Aktivit 1", projekt);
    }

    @org.junit.Test()
    public void inputsetA() {
       org.junit.Assert.assertThrows(AssertionError.class, () -> m.fjernProjekt(new Projekt("Projekt 2", "03-2023")));
    }

    @org.junit.Test()
    public void inputsetB() {
        org.junit.Assert.assertThrows(AssertionError.class, () -> m.fjernProjekt(null));
    }

    @org.junit.Test()
    public void inputsetC() {
        m.fjernProjekt(projekt);
        org.junit.Assert.assertTrue(m.getAnførteAktiviteter().isEmpty());
    }
}
