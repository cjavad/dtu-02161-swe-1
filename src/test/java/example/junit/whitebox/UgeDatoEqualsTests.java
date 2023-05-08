package example.junit.whitebox;

import internal.ProjektPlanningApp;
import internal.UgeDato;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UgeDatoEqualsTests {

    UgeDato compare = new UgeDato(2022, 10);

    @org.junit.Test
    public void inputsetA1() {
        assertFalse(compare.equals(null));
    }

    @org.junit.Test
    public void inputsetA2() {
        assertFalse(compare.equals(new Object()));
    }

    @org.junit.Test
    public void inputsetB1() {
        UgeDato ikkeUge = new UgeDato(compare.getÅrstal(), compare.getUge() - 1);
        assertFalse(compare.equals(ikkeUge));
    }

    @org.junit.Test
    public void inputsetB2() {
        UgeDato ikkeÅr = new UgeDato(compare.getÅrstal() - 1, compare.getUge());
        assertFalse(compare.equals(ikkeÅr));
    }

    @org.junit.Test
    public void inputsetB3() {
        UgeDato ikkeNoget = new UgeDato(compare.getÅrstal() - 1, compare.getUge() - 1);
        assertFalse(compare.equals(ikkeNoget));
    }

    @org.junit.Test
    public void inputsetC() {
        UgeDato pass = new UgeDato(compare.getÅrstal(), compare.getUge());
        assertTrue(compare.equals(pass));
    }

}
