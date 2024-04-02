package Taxes;
import java.util.Scanner;

public class IncomeTaxCalculator {
    /**
     Calculate income tax based on the gross income.
     **/
    
    public static void main(String[] args)   {
        // Take user income input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your income: GHS ");
        double income = scanner.nextDouble();
        scanner.close();

        // Calculate tax and take home pay and print it out
        if (income < 0) {
            System.out.println("Please enter a number greater than or equal 0");
        } else {
            double netIncome = IncomeTax(income);
            double totalTax = income - netIncome;
            
            System.out.println("Take home is: GHS " + netIncome);
            System.out.println("Total tax is: GHS " + totalTax);
        }
        
    }

    static double IncomeTax(double grossIncome) {

        double taxable = grossIncome - 402;
        double totalTax = 0;

        // Check if taxable amount is greater than 0
        if (taxable > 0)  {

            //Tax bracket of less than 110 excess income
            if (taxable <= 110) {
                totalTax += 0.05 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 5.5;
                taxable -= 110;
            }

            //Tax bracket of less than 130 excess income
            if (taxable <= 130) {
                totalTax += 0.1 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 13;
                taxable -= 130;
            }

            //Tax bracket of less than 3000 excess income
            if (taxable <= 3000) {
                totalTax += 0.175 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 525;
                taxable -= 3000;
            }

            //Tax bracket of less than 16395 excess income
            if (taxable <= 16395) {
                totalTax += 0.25 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 4098.75;
                taxable -= 16395;
            }

            //Tax bracket of less than 29963 excess income
            if (taxable <= 29963) {
                totalTax += 0.3 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 8988.90;
                taxable -= 29963;
            }

            //Tax bracket of less than 50000 excess income
            if (taxable <= 50000) {
                totalTax += 0.35 * taxable;
                return (grossIncome - totalTax);
            } else {
                totalTax += 17500;
                return (grossIncome - totalTax);
            }

        // Handle the case where income is exactly 0
        } else {
            return (grossIncome - totalTax);
        }
    }
}