package example.junit.whitebox;

import internal.Aktivitet;
import internal.UgeDato;

public class IsLegalDatoAssignmentTests {

    Aktivitet aktivitet;

    UgeDato startdato;
    UgeDato slutdato;


    @org.junit.Before()
    public void setup() {
        aktivitet = new Aktivitet("Aktivitet");
        startdato = new UgeDato(2023, 1);
        slutdato = new UgeDato(2023, 2);
    }

    @org.junit.Test()
    public void inputsetA() {
        org.junit.Assert.assertTrue(aktivitet.isLegalDatoAssignment(null, null));
    }

    @org.junit.Test()
    public void inputsetB() {
        org.junit.Assert.assertTrue(aktivitet.isLegalDatoAssignment(startdato, null));
    }

    @org.junit.Test()
    public void inputsetC() {
        org.junit.Assert.assertTrue(aktivitet.isLegalDatoAssignment(null, slutdato));
    }

    @org.junit.Test()
    public void inputsetD() {
        org.junit.Assert.assertTrue(aktivitet.isLegalDatoAssignment(startdato, slutdato));
    }

    @org.junit.Test()
    public void inputsetE() {
        org.junit.Assert.assertFalse(aktivitet.isLegalDatoAssignment(slutdato, startdato));
    }
}
