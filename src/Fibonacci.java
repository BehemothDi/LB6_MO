import java.util.Arrays;

public class Fibonacci {
    public static int count_func = 0;

    private static double f(double x){
        count_func++;
        System.out.println("function has been calculated " + count_func +" times");
        return x*x;
    }

    public static double recursive_n(double x_start, double x_stop, int n){

        double length = x_stop - x_start;
        double[] fib_sequence = generate_fib_sequence(n);

        double[] fib = last_three_of_array(fib_sequence);

        double factor_left = fib[2] / fib[0];
        double factor_right = fib[1] / fib[0];

        double x_left = x_start + factor_left * length;
        double x_right = x_start + factor_right * length;

        double f_left = f(x_left), f_right = f(x_right);

        return minimize_recursive(
                x_start,
                x_stop,
                new double[] {x_left, f_left},
                new double[] {x_right, f_right},
                cut_last(fib_sequence)
                );
    }

    public static double recursive_eps(double x_start, double x_stop, double eps) {
        double length = x_stop - x_start;
        int n = (int) Math.ceil(length / (eps * 2.0));
        System.out.println(n);

        double[] fib_sequence = generate_fib_sequence(n);

        double[] fib = last_three_of_array(fib_sequence);

        double factor_left = fib[2] / fib[0];
        double factor_right = fib[1] / fib[0];

        double x_left = x_start + factor_left * length;
        double x_right = x_start + factor_right * length;

        double f_left = f(x_left), f_right = f(x_right);

        return minimize_recursive(
                x_start,
                x_stop,
                new double[] {x_left, f_left},
                new double[] {x_right, f_right},
                cut_last(fib_sequence)
        );
    }

    private static double minimize_recursive(double x_start, double x_stop, double[] left, double[] right, double[] fibs) {
        double length = x_stop - x_start;

        if (fibs.length == 3) return x_start + length / 2;

        double[] fib = last_three_of_array(fibs);

        double factor_left = fib[2] / fib[0];
        double factor_right = fib[1] / fib[0];

        double x_left = left[0], f_left = left[1];
        double x_right = right[0], f_right = right[1];

        System.out.println(Arrays.toString(fibs) +  " length: " + fibs.length);
        System.out.printf("%10f%10f%10f%10f%10f%10f%10f\n\n", length, x_start, x_stop, x_left, x_right, f_left, f_right);


        if (f_left < f_right) {
            double new_length = x_right - x_start;
            double new_x_left = x_start + factor_left * new_length;

            return minimize_recursive(x_start, x_right,
                    new double[] {new_x_left, f(new_x_left)},
                    new double[] {x_left, f_left},
                    cut_last(fibs));

        }

        else {
            double new_length = x_stop - x_left;
            double new_x_right = x_left + factor_right * new_length;

            return minimize_recursive(x_left, x_stop,
                    new double[] {x_right, f_right},
                    new double[] {new_x_right, f(new_x_right)},
                    cut_last(fibs));
        }


    }

    // Вычисление N членов последовательности Фибоначчи
    private static double[] generate_fib_sequence(int n){
        if (n == 1) return new double[] {1};
        if (n == 2) return new double[] {1, 1};

        double[] result = new double[n];
        result[0] = result[1] = 1;
        int k = 2;

        while (k != n) {
            result[k] = result[k-1] + result[k-2];
            k++;
        }

        return result;

    }

    private static double[] last_three_of_array(double[] array){
        int n = array.length - 1;
        return new double[] {array[n], array[n-1], array[n-2]};
    }

    private static double[] cut_last(double[] array){
        return Arrays.copyOf(array, array.length - 1);
    }

    public static void first_100_fibonacci(){
        double[] fibs = generate_fib_sequence(100);

        System.out.print("F(n-2)/F(n): ");
        for (int i = 2; i <100; i++) {
            System.out.printf("%10f", fibs[i - 2] / fibs[i]);
        }
        System.out.print("\nF(n-1)/F(n): ");
        for (int i = 2; i <100; i++) {
            System.out.printf("%10f", fibs[i - 1] / fibs[i]);
        }


    }
}
