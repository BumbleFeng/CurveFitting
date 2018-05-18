package GA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GA {

    private static Logger log = LogManager.getLogger(GA.class.getName());
    private String path;
    private int scale;
    private int g;
    private int maxG;
    private ArrayList<Double[]> target;

    private double bestF;
    private Coefficient bestC;
    private PriorityQueue<Coefficient> pq;

    private int degree;
    private Coefficient[] population;
    private double survivalRate;
    private int s;
    private double crossoverRate;
    private double mutateRate;

    private Random random;

    public GA(String path, int scale, int degree, int maxG, double survivalRate, double crossoverRate, double mutateRate) {
        this.path = path;
        this.scale = scale;
        this.degree = degree;
        this.maxG = maxG;
        this.survivalRate = survivalRate;
        this.crossoverRate = crossoverRate;
        this.mutateRate = mutateRate;

    }

    public void init() {
        target = new ArrayList<>();
        try {
            BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String str;
            while ((str = data.readLine()) != null) {
                String[] s = str.split("\t");
                Double[] p = new Double[2];
                p[0] = Double.valueOf(s[0]);
                p[1] = Double.valueOf(s[1]);
                target.add(p);
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        g = 0;
        bestF = Double.MAX_VALUE;
        population = new Coefficient[scale];
        for (int i = 0; i < scale; i++) {
            population[i] = new Coefficient(degree);
        }
        random = new Random(System.nanoTime());
        s = (int) (scale * survivalRate);
    }

    public void fitness(Coefficient c) {
        double fitness = 0;
        for (Double[] d : target) {
            double x = d[0];
            double y = 0;
            for (int i = 0; i < degree; i++) {
                y += Math.pow(x, i) * c.coe[i];
            }
            fitness += Math.pow(d[1] - y, 2);
        }
        c.fitness = fitness;
    }

    public void evaluate() {
        pq = new PriorityQueue<>();
        for (Coefficient c : population) {
            fitness(c);
            pq.add(c);
        }
        bestC = pq.peek().clone();
        bestF = bestC.fitness;
        System.out.println(g + " " + bestF);
        log.info(g + " " + bestF);
    }

    public void evolve() {
        Coefficient[] next = new Coefficient[scale];
        Coefficient[] survival = new Coefficient[s];
        for (int i = 0; i < s; i++) {
            survival[i] = pq.poll();
        }
        next[0] = bestC;
        for (int i = 1; i < scale; i++) {
            if (random.nextDouble() < crossoverRate) {
                Coefficient father = survival[random.nextInt(s)];
                Coefficient mother = survival[random.nextInt(s)];
                next[i] = crossover(father, mother);
            } else {
                next[i] = survival[random.nextInt(s)].mutate(mutateRate);
            }
        }
        population = next;
        g++;
    }

    public Coefficient crossover(Coefficient father, Coefficient mother) {
        Coefficient child = new Coefficient(degree);
        for (int i = 0; i < degree; i++) {
            if (random.nextBoolean()) {
                child.coe[i] = father.coe[i];
            } else {
                child.coe[i] = mother.coe[i];
            }
        }
        return child;
    }

    public double getBestF() {
        return bestF;
    }

    public PriorityQueue<Coefficient> getPq() {
        return pq;
    }

    public Coefficient[] getPopulation() {
        return population;
    }

    public static void main(String[] args) {
        GA ga = new GA("Data.txt", 2000, 7, 1000, 0.5, 0.8, 0.1);
        ga.init();
        while (ga.g <= ga.maxG) {
            ga.evaluate();
            ga.evolve();
        }
        System.out.println(Arrays.toString(ga.bestC.coe));
        log.info(Arrays.toString(ga.bestC.coe));
    }
}
