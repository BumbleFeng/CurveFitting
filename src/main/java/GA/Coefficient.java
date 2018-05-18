package GA;

import java.util.Arrays;
import java.util.Random;

public class Coefficient implements Comparable<Coefficient> {
    private int degree;
    protected double[] coe;
    protected double fitness;
    private Random random;
    public Coefficient(int degree) {
        this.degree = degree;
        coe = new double[degree];
        random = new Random(System.nanoTime());
        for (int i = 0; i < degree; i++) {
            coe[i] = random.nextDouble()*500 - 250;
        }
    }

    public Coefficient mutate(double p) {
        Coefficient c = this.clone();
        for (int i = 0; i < degree; i++) {
            if (random.nextDouble() < p) {
                if (random.nextBoolean()) {
                    c.coe[i] += random.nextDouble()*500 - 250;
                } else {
                    int j = random.nextInt(degree);
                    double t = c.coe[i];
                    c.coe[i] = c.coe[j];
                    c.coe[j] = t;
                }
            }
        }
        return c;
    }

    @Override
    public Coefficient clone() {
        Coefficient c = new Coefficient(this.degree);
        c.coe = this.coe.clone();
        c.fitness = this.fitness;
        return c;
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Coefficient that = (Coefficient) o;
        return this.degree == that.degree
                && Arrays.equals(this.coe, that.coe);
    }
    
    @Override
    public int hashCode(){
        return 31*this.degree 
                + 31* Arrays.hashCode(this.coe);
    }
    
    @Override
    public String toString(){
        return this.degree + Arrays.toString(this.coe);
    }
    
    @Override
    public int compareTo(Coefficient that) {
        return Double.compare(this.fitness, that.fitness);
    }

    public double getFitness() {
        return fitness;
    }

}
