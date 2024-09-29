public class Polynomial {
	double[] coefficient; // Array to store non-zero coefficients
	int[] exponent; // Array to store corresponding exponents
	
	public Polynomial() {
		coefficient = new double[0]; // Empty array for coefficients
    		exponent = new int[0]; // Empty array for exponents
	}

	public Polynomial(double[] coefficients, int[] exponents) {
		coefficient = new double[coefficients.length];
		for (int i = 0; i < coefficients.length; i++){
			coefficient[i] = coefficients[i];
			exponent[i] = new exponent[exponents.length];
		}
	}
	
    	public Polynomial add(Polynomial y) {
		int totalLength = coefficients.length + y.coefficients.length;
        	double[] sumCoefficients = new double[totalLength];
        	int[] sumExponents = new int[totalLength];

		// Copy elements from first arrays into new arrays
        	int index = 0;
        	for (int i = 0; i < coefficients.length; i++, index++) {
            		sumCoefficients[index] = coefficients[i];
            		sumExponents[index] = exponents[i];
        	}

		// Add or combine terms from the second polynomial
		for (int i = 0; i < y.coefficients.length; i++) {
			boolean inList = false;
		
			// Check if the exponent already exists in the sum
			for (int j = 0; j < index; j++) {
			    if (y.exponents[i] == sumExponents[j]) {
				sumCoefficients[j] += y.coefficients[i];  // Combine coefficients
				inList = true;
				break;
			    }
			}
		
			// If not in list, add the new term to the sum
			if (!inList) {
				sumCoefficients[index] = y.coefficients[i];
				sumExponents[index] = y.exponents[i];
				index++;
			}
		}
		
		// Filter out any zero coefficients by counting non-zero terms
		int nonZeroCount = 0;
		for (int i = 0; i < index; i++) {
			if (sumCoefficients[i] != 0) {
			    nonZeroCount++;
			}
		}

		// Create new arrays to hold only non-zero terms
		double[] newCoefficients = new double[nonZeroCount];
		int[] newExponents = new int[nonZeroCount];
		
		// Fill in the non-zero terms
		int newIndex = 0;
		for (int i = 0; i < index; i++) {
			if (sumCoefficients[i] != 0) {
				newCoefficients[newIndex] = sumCoefficients[i];
				newExponents[newIndex] = newExponents[i];
				newIndex++;
			}
		}
		
		// Return the new polynomial with the non-zero terms
		return new Polynomial(newCoefficients, newExponents);
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
		int maxLength = coefficients.length * y.coefficients.length;  // Max possible number of terms
		double[] productCoefficients = new double[maxLength];
		int[] productExponents = new int[maxLength];
		int index = 0;

	        // Multiply every term in the current polynomial by every term in the other polynomial
	        for (int i = 0; i < coefficients.length; i++) {
	            for (int j = 0; j < y.coefficients.length; j++) {
	                double newCoefficient = coefficients[i] * y.coefficients[j];
	                int newExponent = exponents[i] + y.exponents[j];
	
	                // Check if a term with the same exponent already exists
	                boolean inList = false;
	                for (int k = 0; k < index; k++) {
	                    if (productExponents[k] == newExponent) {
	                        productCoefficients[k] += newCoefficient;
	                        inList = true;
	                        break;
	                    }
	                }
	
	                // If no such term exists, add the new term
	                if (!inList) {
	                    productCoefficients[index] = newCoefficient;
	                    productExponents[index] = newExponent;
	                    index++;
	                }
	            }
	        }
	
	        // Filter out terms with zero coefficients
	        int nonZeroCount = 0;
	        for (int i = 0; i < index; i++) {
	            if (productCoefficients[i] != 0) {
	                nonZeroCount++;
	            }
	        }
	
	        // Create arrays for the result polynomial with only non-zero terms
	        double[] newCoefficients = new double[nonZeroCount];
	        int[] newExponents = new int[nonZeroCount];
	
	        int newIndex = 0;
	        for (int i = 0; i < index; i++) {
	            if (productCoefficients[i] != 0) {
	                newCoefficients[newIndex] = productCoefficients[i];
	                newExponents[newIndex] = productExponents[i];
	                newIndex++;
	            }
	        }
	
	        // Return the resulting polynomial
	        return new Polynomial(newCoefficients, newExponents);
			
	}
}

