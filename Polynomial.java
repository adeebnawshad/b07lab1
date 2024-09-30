import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	double[] coefficient; // Array to store non-zero coefficient
	int[] exponent; // Array to store corresponding exponent
	
	public Polynomial() {
		coefficient = new double[0]; // Empty array for coefficient
    		exponent = new int[0]; // Empty array for exponent
	}

	public Polynomial(double[] coefficient, int[] exponent) {
		coefficient = new double[coefficient.length];
		for (int i = 0; i < coefficient.length; i++){
			coefficient[i] = coefficient[i];
			exponent[i] = new exponent[exponent.length];
		}
	}

	public Polynomial(File file) throws FileNotFoundException {
		// Read the file contents
        	Scanner scanner = new Scanner(file);
       	 	String polynomialString = scanner.nextLine();
        	scanner.close();

		// Lists to temporarily store coefficient and exponent
        	ArrayList<Double> coefficientList = new ArrayList<>();
        	ArrayList<Integer> exponentList = new ArrayList<>();

		// Split the string into terms based on "+" or "-"
        	String[] terms = polynomialString.split("(?=\\+|-)");
		for (String term : terms) {
			double coefficient;
			int exponent;
			// Process terms with 'x'
			if (term.contains("x")) {
				String[] parts = term.split("x");
				// Parse coefficient
				if (parts[0].equals("") || parts[0].equals("+")) {
					coefficient = 1.0;
				} else if (parts[0].equals("-")) {
					coefficient = -1.0;
				} else {
					coefficient = Double.parseDouble(parts[0]);
				}
				// Parse exponent
				if (parts.length > 1 && !parts[1].equals("")) {
					exponent = Integer.parseInt(parts[1]);
				} else {
					exponent = 1;  // default exponent if only 'x'
				}
			} else {
		                // It's a constant term
		                coefficient = Double.parseDouble(term);
		                exponent = 0;
			}
			// Add to lists
			coefficientList.add(coefficient);
			exponentList.add(exponent);
		}
	        // Convert lists to arrays
	        coefficient = new double[coefficientList.size()];
	        exponent = new int[exponentList.size()];
	        for (int i = 0; i < coefficientList.size(); i++) {
	            coefficient[i] = coefficientList.get(i);
	            exponent[i] = exponentList.get(i);
	        }
	    }
	}
    	public Polynomial add(Polynomial y) {
		int totalLength = coefficient.length + y.coefficient.length;
        	double[] sumcoefficient = new double[totalLength];
        	int[] sumexponent = new int[totalLength];

		// Copy elements from first arrays into new arrays
        	int index = 0;
        	for (int i = 0; i < coefficient.length; i++, index++) {
            		sumcoefficient[index] = coefficient[i];
            		sumexponent[index] = exponent[i];
        	}

		// Add or combine terms from the second polynomial
		for (int i = 0; i < y.coefficient.length; i++) {
			boolean inList = false;
		
			// Check if the exponent already exists in the sum
			for (int j = 0; j < index; j++) {
			    if (y.exponent[i] == sumexponent[j]) {
				sumcoefficient[j] += y.coefficient[i];  // Combine coefficient
				inList = true;
				break;
			    }
			}
		
			// If not in list, add the new term to the sum
			if (!inList) {
				sumcoefficient[index] = y.coefficient[i];
				sumexponent[index] = y.exponent[i];
				index++;
			}
		}
		
		// Remove any zero coefficient by counting non-zero terms
		int nonZeroCount = 0;
		for (int i = 0; i < index; i++) {
			if (sumcoefficient[i] != 0) {
			    nonZeroCount++;
			}
		}

		// Create new arrays to hold only non-zero terms
		double[] newcoefficient = new double[nonZeroCount];
		int[] newexponent = new int[nonZeroCount];
		
		// Fill in the non-zero terms
		int newIndex = 0;
		for (int i = 0; i < index; i++) {
			if (sumcoefficient[i] != 0) {
				newcoefficient[newIndex] = sumcoefficient[i];
				newexponent[newIndex] = newexponent[i];
				newIndex++;
			}
		}
		
		// Return the new polynomial with the non-zero terms
		return new Polynomial(newcoefficient, newexponent);
	}

	public double evaluate(double x) {
        	double result = 0;
        	for (int i = 0; i < coefficient.length; i++) {
            		result += coefficient[i] * Math.pow(x, exponent[i]);
        	}
        	return result;
    	}
	public boolean hasRoot(double x) {
        	return evaluate(x) == 0;
	}

	public Polynomial multiply(Polynomial y) {
		int maxLength = coefficient.length * y.coefficient.length;  // Max possible number of terms
		double[] productcoefficient = new double[maxLength];
		int[] productexponent = new int[maxLength];
		int index = 0;

	        // Multiply every term in the current polynomial by every term in the other polynomial
	        for (int i = 0; i < coefficient.length; i++) {
	            for (int j = 0; j < y.coefficient.length; j++) {
	                double newCoefficient = coefficient[i] * y.coefficient[j];
	                int newExponent = exponent[i] + y.exponent[j];
	
	                // Check if a term with the same exponent already exists
	                boolean inList = false;
	                for (int k = 0; k < index; k++) {
	                    if (productexponent[k] == newExponent) {
	                        productcoefficient[k] += newCoefficient;
	                        inList = true;
	                        break;
	                    }
	                }
	
	                // If no such term exists, add the new term
	                if (!inList) {
	                    productcoefficient[index] = newCoefficient;
	                    productexponent[index] = newExponent;
	                    index++;
	                }
	            }
	        }
	
	        // Remove terms with zero coefficient
	        int nonZeroCount = 0;
	        for (int i = 0; i < index; i++) {
	            if (productcoefficient[i] != 0) {
	                nonZeroCount++;
	            }
	        }
	
	        // Create arrays for the result polynomial with only non-zero terms
	        double[] newcoefficient = new double[nonZeroCount];
	        int[] newexponent = new int[nonZeroCount];
	
	        int newIndex = 0;
	        for (int i = 0; i < index; i++) {
	            if (productcoefficient[i] != 0) {
	                newcoefficient[newIndex] = productcoefficient[i];
	                newexponent[newIndex] = productexponent[i];
	                newIndex++;
	            }
	        }
	
	        // Return the resulting polynomial
	        return new Polynomial(newcoefficient, newexponent);
	}
	public void saveToFile(String fileName) throws IOException {
        // Step 1: Convert polynomial to a string
        StringBuilder polynomialString = new StringBuilder();
        
        for (int i = 0; i < coefficient.length; i++) {
            double coeff = coefficient[i];
            int exp = exponent[i];
            
            // Handle the coefficient
            if (i != 0) {
                if (coeff > 0) {
                    polynomialString.append("+");
                }
            }
            if (coeff == -1.0 && exp != 0) {
                polynomialString.append("-");
            } else if (coeff != 1.0 || exp == 0) {
                polynomialString.append(coeff);
            }

            // Handle the exponent
            if (exp != 0) {
                polynomialString.append("x");
                if (exp != 1) {
                    polynomialString.append(exp);
                }
            }
        }

        // Step 2: Write the polynomial string to a file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(polynomialString.toString());
        }
    }
}

