# Pima CC CIS279 Homework Assignment #0

Equal Deposit Investment Calculator (EDIC)

This program/class calculates the ending balance of a series of equal monthly deposits plus accrued interest. The user supplies three (3) values, a monthly deposit, annual interest rate and the number of months to process. The interest is applied monthly. The program outputs a table of monthly totals and ending summation/statistics. 
  
Assumptions:
* Deposit amount must be between 0.01 and $10,000.
* Interest rate must fall between 0 and 25%.
* Number of months must be between 1 and 1200 (100 years).
* Calculations assume a full month of accrued interest.
* Only catches $, € or £ symbols for currency.

Notes: 
* Use of double to represent currency is widely considered a bad choice. Use of Integer or BigDecimal circumvents floating-point errors.
* Compiled with java SE JDK 8, Update 121 (JDK 8u121).
