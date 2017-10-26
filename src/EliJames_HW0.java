/*************************************************************************
 * Title: Equal Deposit Investment Calculator (EDIC)
 * File: EliJames_HW0.java
 * Author: James Eli
 * Date: 1/20/2017
 *
 * This program/class calculates the ending balance of a series of equal 
 * monthly deposits plus accrued interest. The user supplies three (3)
 * values, a monthly deposit, annual interest rate and the number of 
 * months to process. The interest is applied monthly. The program outputs 
 * a table of monthly totals and ending summation/statistics. 
 *  
 * Assumptions:
 *  (1) Deposit amount must be between 0.01 and $10,000.
 *  (2) Interest rate must fall between 0 and 25%.
 *  (3) Number of months must be between 1 and 1200 (100 years).
 *  (4) Calculations assume a full month of accrued interest.
 *  (5) Only catches $, € or £ symbols for currency.
 *
 * Notes: 
 *   (1) Use of double to represent currency is widely considered a bad
 *   choice. Use of Integer or BigDecimal circumvents floating-point errors.
 *   (2) Compiled with java SE JDK 8, Update 121 (JDK 8u121).
 * 
 * Submitted in partial fulfillment of the requirements of PCC CIS-279.
 *************************************************************************
 * Change Log:
 *   01/20/2017: Initial release. JME
 *************************************************************************/
//Scanner class used for user input.
import java.util.Scanner;

public class EliJames_HW0 {
  // Constants.
  private static final int MIN_MONTHS = 1;                    // Minimum months to run calculations.
  private static final int MAX_MONTHS = 1200;                 // Maximum months to run calculations.
  private static final double MIN_INTEREST_RATE = 0.0f;       // Minimum annual interest rate. 
  private static final double MAX_INTEREST_RATE = 25.0f;      // Maximum annual interest rate.
  private static final double MIN_MONTHLY_DEPOSIT = 0.01f;    // Minimum monthly deposit.
  private static final double MAX_MONTHLY_DEPOSIT = 10000.0f; // Maximum monthly deposit.
  private enum InputStatus { INVALID, VALID };                // Input loop validation flag. 

  /*********************************************************************
   * Start of main program. Command line arguments are ignored.
   *********************************************************************/
  public static void main( String[] args ) {
    Scanner scanner = new Scanner( System.in ); // All keyboard input is handled through this class.
    String s;                         // String temporarily holds user input before conversion.
    InputStatus inputStatus;          // Flag value indicates valid non-negative numeric input.
    int totalMonths = 0;              // Number of months to run interest calculations.
    double monthlyDeposit = 0.0f;     // Amount of recurring monthly deposit.
    double annualInterestRate = 0.0f; // Annual interest rate used in calculations.

    // Display program header.
    System.out.println( "Equal Monthly Deposit Interest Calculator\r\n" );

    /*********************************************************************
     * Validate monthly deposit input.
     *********************************************************************/
    // Assume invalid input for monthly deposit.
    inputStatus = InputStatus.INVALID;
    
    // Infinite loop, we "break" out upon receiving valid input.
    while( inputStatus == InputStatus.INVALID ) {
      try { // Catch exceptions in this input block.
        // Display a prompt string.
        System.out.println( "Enter recurring monthly deposit ($0.01 to $10,000.00):" );
        // Capture input as a string, and trim leading/trailing whitespace.
        s = scanner.nextLine().trim();
        // Test input string with regular expression parser:
        // "optional currency symbol, (optional digits and comma) or (any number of digits) followed by
        // (optional period) and (optionally up to 2 digits) and trailing optional currency symbol".
        // This should catch most obvious currency entry. However, it doesn't catch the reverse use of '.' and ','.
          if ( s.matches( "[\\$\\€\\£]?(\\d{1,3}(\\,\\d{3})*|\\d*)(\\.)?(\\d{0,2})?[\\$\\€\\£]?" ) ) {
          // Input fits basic pattern of currency, ensuring string length 1st.
          if ( s.length() >= 1 ) {
            // Eliminate currency symbols and comma characters. 
            s = s.replace( "$", "" ).replace( "€", "" ).replace( "£", "" ).replace( ",", "" );
          }
          // Convert string to double. 
          monthlyDeposit = Double.parseDouble( s );
          // Check if entry exceeds min/max limits.
          if ( monthlyDeposit < MIN_MONTHLY_DEPOSIT || monthlyDeposit > MAX_MONTHLY_DEPOSIT ) {
            // User entered a rate outside limits. Alert them.
            System.out.format( "Value must be between: %2.2f and %2.2f.%n", MIN_MONTHLY_DEPOSIT, MAX_MONTHLY_DEPOSIT );
          } else {
            // Arriving here means the input passes all of our validation checks
            inputStatus = InputStatus.VALID; // Break out of the infinite while loop.
          }
        } else { // Failed regular expression match.
          // Input was invalid, (probably included non-digit chars). Alert user and try again.
          System.out.println( "Please enter numbers only." );
        }
      /* 
       * Possible exceptions thrown inside the try braces are:
       * parseDouble & replace:
       *   NullPointerException - if string is null. (e1)
       *   NumberFormatException - if string does not contain a parsable double. (e2)
       * nextLine:
       *   NoSuchElementException - if no line was found. (e2)
       */
      } catch ( IndexOutOfBoundsException | NullPointerException e1 ) {
        // Empty string received as input. Alert and try again.
        System.out.println( "Empty input received, please try again." );
      } catch ( Exception e2 ) {
        // It should be possible to retry. Alert and try again.
        System.out.println( "I don't understand your input, please try again." );
      }
    } // End of monthlyDeposit while loop.

    /*********************************************************************
     * Validate interest rate input.
     *********************************************************************/
    // Assume invalid input for annual interest rate.
    inputStatus = InputStatus.INVALID;

    // Infinite loop, we "break" out upon receiving valid input.
    while( inputStatus == InputStatus.INVALID ) {
      // Catch exceptions in this input block.
      try {
        // Display a prompt string.
        System.out.format( "Enter the annual interest rate (%2.1f to %2.1f%%):%n", MIN_INTEREST_RATE, MAX_INTEREST_RATE );
        // Capture input as a string, and trim leading/trailing whitespace.
        s = scanner.nextLine().trim();
        // Test the input string with the regular expression parser, using
        // expression == [optional '+', any number of digits (optional period 
        // and optionally any number of digits) and optional '%' symbol]. 
        // This should eliminate SN, but allow obvious matches.
        if ( s.matches( "[\\+]?\\d*(\\.\\d*)?%?" ) ) {
          // Input fits basic pattern of percentage, ensure string length 1st.
          if ( s.length() >= 1 )
            s = s.replace("%", ""); // Remove '%' symbol.
          annualInterestRate = Double.parseDouble( s ); // Convert string to double.
          // Check if entry exceeds min/max limits.
          if ( annualInterestRate < MIN_INTEREST_RATE || annualInterestRate > MAX_INTEREST_RATE ) {
            // User entered a rate outside limits. Alert them.
            System.out.format( "Value must be between: %2.1f and %2.1f.%n", MIN_INTEREST_RATE, MAX_INTEREST_RATE );
          } else {
            // Arriving here means the input passes all of our validation checks 
            inputStatus = InputStatus.VALID; // Break out of the infinite while loop.
          }
        } else { // Failed regular expression match.
          // Input was invalid, (probably included non-digit chars). Alert user and try again.
          System.out.println( "Please enter numbers only." );
        }
      // See above note for possible exceptions thrown inside the try braces.
      } catch ( IndexOutOfBoundsException | NullPointerException e1 ) {
        // Empty string received as input. Alert and try again.
        System.out.println( "Empty input received, please try again." );
      } catch ( Exception e2 ) {
        // It should be possible to retry. Alert and try again.
        System.out.println( "I don't understand your input, please try again." );
      }
    } // End of annualInterestRate while loop.

    /*********************************************************************
     * Validate number of months input.
     *********************************************************************/
    // Assume invalid input for number of months.
    inputStatus = InputStatus.INVALID;

    // Prompt/input/validate loop.
    do {
      try {
        // Prompt user for input.
        System.out.println( "Enter the number of months (1 to 1200): " );
        // Check if user entered an integer.
        if ( scanner.hasNextInt() ) {
          // Get integer.
          totalMonths = scanner.nextInt();
          // test for a positive integer.
          if ( totalMonths >= MIN_MONTHS && totalMonths <= MAX_MONTHS ) {
            // Positive integer. Flag that good input received so do/while loop terminates.
            inputStatus = InputStatus.VALID;
          } else {
            // User input a negative integer. Alert and continue loop.
            System.out.println( "Input must be whole number between: " + MIN_MONTHS + " and " + MAX_MONTHS + "." );
          }
        } else {
          // Input was not an integer. Discard input and continue loop.
          scanner.nextLine();
        }
      } catch ( Exception e ) {
        // Catch an input exception here.
        System.out.println( "Unexpected input received. Try again." );      
      }
      //loop until valid non-negative integer is received.
    } while ( inputStatus == InputStatus.INVALID ); // End of totalMonths do/while loop. 
    
    // Input is complete.
    scanner.close();
    
    /*********************************************************************
     * Calculate and display deposit/interest statistics.
     *********************************************************************/
    int numMonthsAccrued;               // Number of months of accrued interest.
    double compoundMonthlyInterestRate; // Compounded monthly interest.
    double totalAmountAccrued = 0.0F;   // Total amount accrued (deposits plus interest).
    // Monthly interest rate is 1.0 + (annual rate/12 months/100)
    final double monthlyInterestRate = 1. + annualInterestRate / 1200.; 

    // Display tabular data header.
    System.out.println( "       |         | Months of | Monthly  | Compounded       | Deposit           |");
    System.out.println( "       |         | Accrued   | Interest | Interest         | plus              |" );
    System.out.println( " Month | Deposit | Interest  | Rate     | Rate             | Interest          |" );

    // Iterate through each month and display statistics.
    for ( int month=1; month<=totalMonths; month++ ) {
      // calculate number of months of accrued interest.
      numMonthsAccrued = totalMonths - month + 1;
      System.out.printf( " %-4d", month );
      System.out.printf( "%12.2f", monthlyDeposit );
      System.out.printf( "%12d", numMonthsAccrued );
      System.out.printf( "%11.4f", monthlyInterestRate );
      // Calculate compounded monthly interest.
      compoundMonthlyInterestRate = Math.pow( monthlyInterestRate, numMonthsAccrued );
      System.out.printf( "%19.5f", compoundMonthlyInterestRate );
      System.out.printf( "%20.2f%n", (monthlyDeposit*compoundMonthlyInterestRate) );
      // Calculate total amount accrued (deposits plus interest).
      totalAmountAccrued += (monthlyDeposit*compoundMonthlyInterestRate);
    }
    
    // Display final totals.
    System.out.printf( "Sums:%12.2f", (monthlyDeposit*totalMonths) );
    System.out.printf( "%62.2f%n", totalAmountAccrued );
    
    // Normal program terminates here.

  } // End of main module.
  
} // End of EliJames_HW0 class.

