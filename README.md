# Polynomial Curve Fitting
Basic Principle: In mathematics, a Taylor series is a representation of a function as an infinite sum of terms that are calculated from the values of the function’s derivatives at a single point, which means a function can be approximated by using a finite number of terms of its Taylor series.

The goal of this project is to use genetic algorithm to find a set of coefficients for a polynomial function which fits a series of given data well.
# Getting Started
The following variables can be initialized when constructing a GA object.
 - original data path
 - population size
 - polynomial degree
 - maximum generation
 - survival rate
 - crossover rate
 - mutation rate
>example: GA ga = new GA("Data.txt", 2000, 7, 1000, 0.5, 0.8, 0.1);

# Implementation Details

## Genetics
 - Genotype: A double array of coefficients. The index of coefficient is equivalent to each term's degree.
 - Phenotype: A polynomial fitting function. This also describes how the genes are expressed.
>example: f(x)=a+bx+cx^2+dx^3+ex^4
## Mutation
 - Option 1: change coefficient value
 - Option 2: swap two coefficients
## Crossover
For each gene/coefficient, a child inherits father’s or mother’s with equal possibility.

## Fitness/Difference

Using the method of least squares, the difference between the calculated value and the actual value on the Y axis is compared and added point by point. Therefore, the larger the differences, the higher the value of fitness would be. In each population, which is implemented by a priority queue, the top one is the best with the smallest fitness value.

## Selection

During each generation, the fittest one will be directly copied into the next generation.  And  the  next  generation’s population size will continue increasing either by crossing over two randomly selected survivors’ genes or mutating a survivor’s genes until it reaches the predefined scale.


# Observations & Analyses
See [Polynomial Curve Fitting.pptx](https://github.com/meng-tan/INFO6205_536_MengTan/blob/master/Polynomial%20Curve%20Fitting.pptx)
