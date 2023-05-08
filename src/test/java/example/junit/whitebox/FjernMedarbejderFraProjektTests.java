package example.junit.whitebox;

import internal.Medarbejder;
import internal.Projekt;
import internal.SystemApp;
import internal.SystemAppException;
import org.mockito.internal.matchers.Null;

public class FjernMedarbejderFraProjektTests {
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
        org.junit.Assert.assertThrows(AssertionError.class, () -> systemApp.planner.fjernMedarbejderFraProjekt(null, projekt));
    }

    @org.junit.Test()
    public void inputsetB() {
        org.junit.Assert.assertThrows(AssertionError.class, () -> systemApp.planner.fjernMedarbejderFraProjekt(m, null));
    }

    @org.junit.Test()
    public void inputsetC() {
        org.junit.Assert.assertThrows(AssertionError.class, () -> systemApp.planner.fjernMedarbejderFraProjekt(null, null));
    }

    @org.junit.Test()
    public void inputsetD() {
        systemApp.planner.fjernMedarbejderFraProjekt(m, projekt);
        org.junit.Assert.assertTrue(m.getProjekter().isEmpty());
        org.junit.Assert.assertTrue(projekt.getMedarbejder().isEmpty());
    }
}
