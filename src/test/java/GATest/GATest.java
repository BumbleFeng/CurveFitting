package GATest;

import GA.Coefficient;
import GA.GA;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GATest {

    private GA instance;

    public GATest() {
        instance = new GA("Data.txt", 2000, 10, 10000, 0.5, 0.8, 0.1);
        instance.init();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of fitness method, of class GA.
     */
    @Test
    public void testFitness() {
        Coefficient a = new Coefficient(10);
        Coefficient b = new Coefficient(10);
        instance.fitness(a);
        instance.fitness(b);
        assertFalse(a.getFitness() == b.getFitness());
        b = a.clone();
        instance.fitness(a);
        instance.fitness(b);
        assertTrue(a.getFitness() == b.getFitness());
    }

    /**
     * Test of evaluate method, of class GA.
     */
    @Test
    public void testEvaluate() {
        instance.evaluate();
        double best = instance.getBestF();
        double a = Double.MAX_VALUE;
        for (Coefficient c : instance.getPopulation()) {
            a = (c.getFitness() < a) ? c.getFitness() : a;
        }
        assertTrue(best == a);

        Coefficient[] p = new Coefficient[2000];
        for (int i = 0; !instance.getPq().isEmpty(); i++) {
            p[i] = instance.getPq().poll();
        }
        for (int i = 0; i < 1999; i++) {
            assertTrue(p[i].getFitness() <= p[i + 1].getFitness());
        }
    }

    /**
     * Test of evolve method, of class GA.
     */
    @Test
    public void testEvolve() {
        instance.evaluate();
        double best = instance.getBestF();
        instance.evolve();
        instance.evaluate();
        assertTrue(instance.getBestF()<=best);
    }

    /**
     * Test of crossover method, of class GA.
     */
    @Test
    public void testCrossover() {
        Coefficient father = new Coefficient(10);
        Coefficient mother = new Coefficient(10);
        Coefficient child = instance.crossover(father, mother);
        assertFalse(father.equals(mother));
        assertFalse(father.equals(child));
        assertFalse(mother.equals(child));
        child = instance.crossover(father, father);
        assertEquals(father, child);
    }

}