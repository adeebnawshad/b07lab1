import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Testing the no-argument constructor (should represent 0)
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3));  // Evaluate at x=3

        // Testing the constructor with coefficients and exponents
        double[] c1 = {6, 5};  // 6 + 5x^3
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-2, -9};  // -2x + -9x^4
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        // Testing addition
        Polynomial sum = p1.add(p2);
        System.out.println("sum(0.1) = " + sum.evaluate(0.1));  // Evaluate sum at x=0.1

        // Checking for a root
        if (sum.hasRoot(1)) {
            System.out.println("1 is a root of sum");
        } else {
            System.out.println("1 is not a root of sum");
        }

        // Testing multiplication
        Polynomial product = p1.multiply(p2);
        System.out.println("product(2) = " + product.evaluate(2));  // Evaluate product at x=2

        // Saving polynomial to file
        try {
            sum.saveToFile("sum_polynomial.txt");
            System.out.println("Sum polynomial saved to file.");
        } catch (IOException e) {
            System.out.println("Failed to save polynomial: " + e.getMessage());
        }

        // Testing the file constructor
        try {
            Polynomial pFromFile = new Polynomial(new File("sum_polynomial.txt"));
            System.out.println("pFromFile(2) = " + pFromFile.evaluate(2));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
