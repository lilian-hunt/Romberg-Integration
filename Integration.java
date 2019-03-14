import java.util.function.Function;

public class Romberg {
    /**
     * calculates R(n,m) where n = m = degree
     **/
    public static double integrate(Function<Double,Double> f, double a, double b, int n) {
        // trapezoidal rule implemented below
		int m = n;
		double[][] keep = new double[n+1][n+1];
		//basecase 0,0
		double baseCase = h_n(a,b,1)*(f.apply(a)+f.apply(b));
		keep[0][0] = baseCase;
		for (int i = 1; i<=n; i++){
			double fSum = 0;
			for (int k = 1; k <= Math.pow(2,i-1);k++){
				fSum += f.apply(a+h_n(a,b,i)*(2*k-1));
			}
			keep[i][0] = 0.5*keep[i-1][0] + h_n(a,b,i)*fSum;	
		}
		for (int nn = 1; nn <= n; nn++){
		 	for (int mm = 1; mm <= m; mm++){
		 		if (nn >= mm){	
					keep[nn][mm] = ((Math.pow(4,mm)*keep[nn][mm-1]-keep[nn-1][mm-1])/(Math.pow(4,mm)-1));
				}
			}
		}

		return keep[n][n];
    }
    public static double h_n(double a, double b, double n){
		double ret = (1/(Math.pow(2,n)))*(b-a);
		return ret;
	}
    public static void main(String[] args) {
        // tests
        double area, expected;
        
        area = integrate(x -> x*x, 0, 1, 5);
        expected = 0.3333333;
        // 99% accuracy
        assert Math.abs(area - expected) < Math.abs(0.01 * expected);
        
        area = integrate(x -> Math.sin(x), 0, Math.PI, 5);
        expected = 2;
        assert Math.abs(area - expected) < Math.abs(0.01 * expected);
    }
}
