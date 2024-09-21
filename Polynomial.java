public class Polynomial {
	double[] coefficient;
	
	public Polynomial() {
		coefficient = new double[1];
		coefficient[0] = 0;
	}

	public Polynomial(double[] coefficients) {
		coefficient = new double[coefficients.length];
		for (int i = 0; i < coefficients.length; i++){
			coefficient[i] = coefficients[i];
		}
	}
	
    	public Polynomial add(Polynomial y) {
		int maxLength = Math.max(coefficient.length, y.coefficient.length);
		double[] sumCoefficients = new double[maxLength];
		
		for (int i = 0; i < maxLength; i++) {
			double coeff1 = 0;
			double coeff2 = 0;
		
			if (i < coefficient.length) {
				coeff1 = coefficient[i];
			}
			if (i < y.coefficient.length) {
                		coeff2 = y.coefficient[i];
            		}
			sumCoefficients[i] = coeff1 + coeff2;
        	}
        	return new Polynomial(sumCoefficients);
	}

	public double evaluate(double x) {
        	double result = 0;
        	for (int i = 0; i < coefficient.length; i++) {
            		result += coefficient[i] * Math.pow(x,i);
        	}
        	return result;
    	}
	public boolean hasRoot(double x) {
        	return evaluate(x) == 0;
	}	
}

