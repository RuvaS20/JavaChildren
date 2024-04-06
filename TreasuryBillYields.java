
/**
 * Program for computing compound interest with regular contributions.
 * The formula used is:
 * A = P(1 + r/n)^(nt) + C[((1 + r/n)^(nt) - 1)/(r/n)]
 * The formula is adapted from the discussion on Quora:
 * [Compound Interest with Regular
 * Contributions](https://www.quora.com/How-do-we-calculate-compound-interest-if-we-keep-adding
 * -more-principal-amount-every-month-same-amount-or-different-amount-Is-there-a-formula-for-that)
 * 
 * @author Ruvarashe Sadya
 * @version 1.0
 * @since 2024-02-03
*/
import java.util.Scanner;

public class TreasuryBillYields {
    public static void main(String args[]) {

        String cediChar = "\u20B5";

        Scanner scanner = new Scanner(System.in);

        // Receiving principal from user
        System.out.printf("Enter the your initial Principal %s", cediChar);
        double principal = scanner.nextDouble();

        // Receiving rate from user
        System.out.print("Enter the yearly rate (without the % sign): ");
        double rate = scanner.nextDouble();
        rate = rate / 100; // Has to be converted to decimal

        // Receiving number of times interest is compounded each year
        System.out.print("Enter the number of times interest is compounded each year: ");
        double periodsPerYear = scanner.nextDouble();

        // Receiving the amount the user will add to the investment account in each
        // period after the initial one
        System.out.printf(
                "Enter the amount you will add to the investment account in each period after the initial one %s",
                cediChar);
        double deposit = scanner.nextDouble();

        // Receiving the number of periods the investment will accrue over
        System.out.print("Enter the total number of periods (yr) the investment will accrue over: ");
        double totalPeriod = scanner.nextDouble();

        scanner.close();

        // Calculate values for the investment value
        double investmentValue = computeInvestmentValue(principal, rate, periodsPerYear, totalPeriod, deposit);
        double totalInterest = computeTotalInterest(investmentValue, principal, deposit, periodsPerYear);

        // Print amounts rounded to 2.dp
        System.out.printf("Value of investment after %.2f years: %s %.2f \n", totalPeriod, cediChar, investmentValue);
        System.out.printf("Value of interest after %.2f years: %s %.2f\n", totalPeriod, cediChar, totalInterest);
    }

    public static double computeInvestmentValue(double principal, double rate, double periodsPerYear,
            double totalPeriod, double deposit) {
        /**
         * Computes the value of an investment with regular contributions using compound
         * interest.
         * 
         * @param principal      Initial investment at the beginning of the period
         * @param rate           Annual interest rate (in decimal form)
         * @param periodsPerYear Number of times interest is compounded each year
         * @param totalPeriod    Number of periods the investment will accrue over
         * @param deposit        Amount added to the investment account in each
         *                       period after the initial one.
         * @return The calculated value of the investment after the specified number of
         *         periods.
         */

        // Calculating value of investment in the first month
        double firstInvestmentValue = computeGeneralValue(principal, rate, periodsPerYear, totalPeriod);

        // Calculating the value of investment in succeeding months, with additional
        // deposit
        double futureAmount = computeGeneralValue(1, rate, periodsPerYear, totalPeriod); // principal as 1
        double secondInvestmentValue = deposit * ((futureAmount - 1) / (rate / periodsPerYear));

        // Subtract deposit because in the last month the deposit doesn't accrue
        // interest
        double investmentValue = (firstInvestmentValue + secondInvestmentValue) - deposit;
        return investmentValue;
    }

    public static double computeGeneralValue(double principal, double rate, double periodsPerYear, double totalPeriod) {
        /**
         * Calculates the final Amound for any compound Interest using the basic
         * compound Interest formula
         *
         * @return Final Value of the Investement
         */

        double generalValue = principal * Math.pow((1 + (rate / periodsPerYear)), (periodsPerYear * totalPeriod));
        return generalValue;
    }

    public static double computeTotalInterest(double investmentValue, double principal, double deposit,
            double periodsPerYear) {
        /**
         * Computes the total interest earned on an investment with regular
         * contributions.
         *
         * @param investmentValue The final value of the investment after a specified
         *                        number of periods.
         * @return The total interest earned over the specified number of periods.
         */
        double totalInterest = investmentValue - principal - ((periodsPerYear - 1) * deposit);
        return totalInterest;
    }
}
