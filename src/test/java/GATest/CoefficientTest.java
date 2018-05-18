package GATest;

import GA.Coefficient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoefficientTest {

    public CoefficientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of mutate method, of class Coefficient.
     */
    @Test
    public void testClone() {
        Coefficient instance = new Coefficient(10);
        Coefficient result = new Coefficient(10);
        assertFalse(instance.equals(result));
        result = instance.clone();
        assertTrue(instance.equals(result));
        assertEquals(instance.hashCode(), result.hashCode());
        assertEquals(instance, result);
    }

    /**
     * Test of clone method, of class Coefficient.
     */
    @Test
    public void testMutate() {
        Coefficient instance = new Coefficient(10);
        Coefficient result = instance.clone();
        assertEquals(instance, result);
        result = instance.mutate(0);
        assertEquals(instance, result);
        result = instance.mutate(1);
        assertFalse(instance.equals(result));
    }
}
