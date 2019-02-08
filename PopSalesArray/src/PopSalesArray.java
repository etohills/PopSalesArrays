/* 
 * uses array to output data. Only Arrays are allowed in this project
 *  Ethan O'Neill 2/7/2019
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.text.*;
import java.util.*;
import java.io.PrintWriter;



public class PopSalesArray {
	static boolean ErrSw = false;
	static String iString;
	//declaring variables
	static int errors = 0;
	static String ilname, ifname, iaddress, icity, istate;
	static String izip1;
	static String izip2;
	static String icaseNum;
	static double total;
	static int cizip ;
	static int iPopType;
	static String spoptype;
	static String NumCases;
	static int quantity;
	static String iTeam ;
	static int holder;
	static String iDash;
	static PrintWriter pw, errorpw; //write data to report
	static Scanner PopScanner;
	static boolean eof = false;	
	static String record;	
	static int  numcase ;
	static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	static Date date = new Date(); 
	static int PopTypesNum;
	static int popQuantity;
	static int index = -1;
	static String[] PopTypes = {"Coke", "Diet Coke", "Mellow Yellow", "Cherry Coke", "Diet Cherry Coke", "Sprite"};
	//static Integer[] PopNums = new Integer[5];
    static String OutPopType;
    static double oDeposit;
    static String[] Arrstate = {"IA", "IL", "MI", "MO", "NE", "WI"};
    static double[] ArrdepositeAmt = {0.05, 0.00, 0.10, 0.00, 0.05, 0.05 };
    static int Cdeposit = 0;
    static double cTeamGtTotal = 0;
   // static int[] PopNums = new int[PopTypes.length];
    static int[] PopCount = {0, 0, 0, 0, 0, 0};
    static String[] oErrMsg = {
    		"last name can't be blank",                		//0
    		"first name can't be balnk",  					//1 
    		"address can't be blank",    					//2
    		"city can't be blank",							//3
    		"state choices are IA, IL, MI, MO, NE, WI",		//4
    		"zip needs to be numeric",						//5
    		"Pop Types needs to be numeric",				//6
    		"Pop choices are 1 to 6",						//7
    		"Cases need to be numeric",						//8
    		"cases need to be greater than 1",				//9
    		"Team choices are A to E"
    		
    };
    static String OutputError;
    static String OutputMsg;
    static double cGtTotateam;
    static double[] ArrTotalTeamSales = new double[5];
    static String[] ArrTeam = {"A", "B", "C", "D", "E"};
    static String Out  = "" ;
public static void main(String[] args) {
	
		
		//call init()
		init();
		
		
	
		
		//loop until no more records
		
			
		mainline();
		close();
		pw.close();
		errorpw.close();
		
		System.out.println("Program ending, have a good dAY!");
	}
public static void mainline() {
	
	while(!eof) {
	validation();
	
		if (ErrSw == true) {
	
			ErrorReport();
		}
		
		else{
			calcs();
			output();	
	
		
		
		}
		input();
	}
}	

public static void init() {
	//
	for( int i= 0; i<ArrTeam.length ; i++) {
		ArrTotalTeamSales[i] = 0;
	}
			
			
	//count for the pop types to grand total
	for (int i= 0; i<PopCount.length; i++) {
		PopCount[i++] = 0;
	}
	//set scanner to the input file
	try {
		PopScanner = new Scanner(new File("popFun.DAT"));
		PopScanner.useDelimiter(System.getProperty("line.separator"));
	} catch (FileNotFoundException e) {
		
		System.out.println("File error");
		System.exit(1);
	}
	
	

	//initialize the PrintWritter object
	try {
		pw = new PrintWriter(new File ("JAVPOPSLB.PRT "));
			//heading();
		
	} catch (FileNotFoundException e) {
		System.out.println("Output file error with regular file");
	}
	
	try {
		errorpw = new PrintWriter(new File ("JAVPOPERB.PRT "));
		errorheading();
			
		}catch (FileNotFoundException e) {
			System.out.println("Output file error with error file");}

	input();

	heading();

	
}

public static void input() {
	iPopType = 0;
	 //string used to hold entire record being read
	//as long as there are more records to read
	if (PopScanner.hasNext()) {	
		record = PopScanner.next();	
		ilname = record.substring(0, 15);	
		ifname = record.substring(15, 30);
		iaddress = record.substring(30,45);	
		icity  = record.substring(45,55);
		istate = record.substring(55, 57);
		izip1  = record.substring(57,62);
	
		izip2  = record.substring(62,66);	
		//iString = record.substring(66 ,68 ); choice 1 
		iPopType = Integer.parseInt(record.substring(66 ,68 )); //choice 2 
		
		NumCases = record.substring(68 , 70 );
		iTeam = record.charAt (70)+"";
		
	}
	else {		
		eof=true; //no more records set to true

	}
	
	
}
public static void heading() {
	pw.format("%-16s%36s%28s%44s%5s%1s%2s%n", "DATE: " + formatter.format(date),"", "Albia Soccer Club Fundraiser", " ", "Page:", "", "1");
	
	pw.format("%-8s%48s%10s%1s%8s%n", "JAVETO05", " ", "ONeill---- ",""," DIVISION");
	pw.format("%-60s%12s%n", " ", "Sales Report ");
	pw.println();
	pw.format("%-3s%9s%8s%10s%7s%4s%8s%5s%1s%8s%4s%8s%13s%8s%6s%11s%6s%11s%n", "", "Last Name", "", "First Name", " ","City","", "State", "", "Zip Code","", "Pop Type","", "Quantity", "", "Deposit Amt", "", "Total Sales");
	pw.println();
	
	
		
}

public static void errorheading() {
	errorpw.format("%-16s%36s%28s%44s%5s%1s%1s%n", "DATE: " + formatter.format(date),"", "Albia Soccer Club Fundraiser", " ", "Page:", "", "1");
	
	errorpw.format("%-8s%48s%10s%1s%8s%n", "JAVETO05", " ", "ONeill---- ",""," DIVISION");
	errorpw.format("%-60s%12s%n", "", "Error Report ");
	errorpw.format("%-12s%60s%17S%n", "ERROR RECORD", "", "ERROR DESCRIPTION");
	errorpw.println();
}

public static void errordetailheadings() {
	
	errorpw.format("%-71s%1s%10s%n",record  , "",OutputMsg  );
	errorpw.println();
}

public static void detailHeadings() {
	DecimalFormat formatter = new DecimalFormat("#");
	DecimalFormat formatterd = new DecimalFormat("$.00");
	DecimalFormat formattert = new DecimalFormat("$.00");
	 numcase = Integer.parseInt(NumCases);
	Double f = numcase * oDeposit * 24;
	total = 18.71 * numcase + f;
	String NumCase = formatter.format(numcase);
	String DepositAmt = formatterd.format(oDeposit);
	String totalSales = formatterd.format(total);
	pw.format("%-3s%15s%2s%15s%2s%10s%3s%2s%3s%5s%1s%4s%2s%16s%8s%2s%11s%7s%9s%9s%n","", ilname, "", ifname, "", icity, "", istate, "", izip1, "-",izip2, "", OutPopType, "",NumCase, "", DepositAmt, "", totalSales);
	pw.println();
}
public static void calcs() {
	//iPopType -=1;
	OutPopType = PopTypes[iPopType-1];
	//For testing: System.out.println(iPopType);
	
	for(int i = 0; i < Arrstate.length; i++ )
	{
		
		if (istate.equals (Arrstate[i]) ){
			Cdeposit = i;
			
		
		}
		
		

	}
	//using Array called "ArrdepositeAmt" to parelly move data and do calcs properly
	 oDeposit= ArrdepositeAmt[Cdeposit] ;
	//Using Arrays called Popcount to count and sort how many poptypes show up. 
	 PopCount[iPopType-1] += numcase; 
	 
	 
	 
	 for (int i = 0; i < ArrTeam.length; i++) {
	 
		 if( iTeam.equals(ArrTeam[i])) {
			 cTeamGtTotal = i;
			 
		 ArrTotalTeamSales[i] += total;}
			 
	 }
	 
	
}




public static void output() {

	
	detailHeadings();
	
	
	
}

public static  void validation() {
	//setting your error switch to true
	ErrSw = true;
	
	
	  // validating last name
	if(ilname.trim().isEmpty() ) {
		OutputMsg=oErrMsg[0];
		errors += 1;
	return;
	}else {
		
	}
	//validating first name 
	if(ifname.trim().isEmpty() ) {
		OutputMsg=oErrMsg[1]; 
		errors += 1;
	return;
	}else {
		
	}
	//validating address
	
	if(iaddress.trim().isEmpty() ) {
		OutputMsg=oErrMsg[2]; 
		errors += 1;
	return;
	}else {
		
		
		
	}
	//validating city
	if(icity.trim().isEmpty() ) {
		OutputMsg=oErrMsg[3]; 
		errors += 1;
	return;
	}else {
		
		
		
	}
	//validating istate 
	if(istate.trim().isEmpty() ) {
		OutputMsg=oErrMsg[4]; 
		errors += 1;
	return;
	
	}else {
		
	}
	
	//validating first part of izip
	try {
		Integer.parseInt(izip1);
	} catch(NumberFormatException e) {
		OutputMsg=oErrMsg[5];
		errors += 1;
		return;
	}
//validating part 2 of zip	
	try {
		Integer.parseInt(izip2);
	} catch(NumberFormatException e) {
		OutputMsg=oErrMsg[5];
		errors += 1;
		return;
	}
	//validating choices 1 to 6 of popType
	if (iPopType < 0 || iPopType > 6) {
		OutputMsg=oErrMsg[7];
		errors += 1;
		return;
	}
	//validating case numbers
	try {
		Integer.parseInt(NumCases);
	} catch(NumberFormatException e) {
		OutputMsg=oErrMsg[8];
		errors += 1;
		return;
	}
	if (Integer.parseInt(NumCases) != 0 && Integer.parseInt(NumCases) >= 1) {
		
	} else {
		OutputMsg=oErrMsg[9];
		errors += 1;
		return;
	}
	// validating Team, choices are A to E only 
	if (iTeam.charAt(0) == 'A' || iTeam.charAt(0) == 'B' || iTeam.charAt(0) == 'C' || iTeam.charAt(0) == 'D' || iTeam.charAt(0) == 'E') {
		
	} else {
		OutputMsg=oErrMsg[10];
		errors += 1;
		return;
	}
	// turning off Error Switch, very important 
	ErrSw = false;
	
}
	
public static void errorgrandTot() {
	errorpw.format("%-12s%n", "Total Errors: " + errors);
}

public static void grandtotal() {

	
	pw.format("%-13s%n", "Grand Totals:");
	pw.println();
	

	pw.format("%-3s%16s%1s%7s%6s%16s%1s%7s%6s%16s%1s%7s%6s%n", "", PopTypes[0], "",  PopCount[0], "", PopTypes[1], "",  PopCount[1], "", PopTypes[2], "",  PopCount[2], "");
	pw.println();
	pw.format("%-3s%16s%1s%7s%6s%16s%1s%7s%6s%16s%1s%7s%6s%n", "", PopTypes[3], "",  PopCount[3], "", PopTypes[4], "",  PopCount[4], "", PopTypes[5], "",  PopCount[5], "");
	
	
	pw.println();
	pw.println();
	
	pw.format("%-13s%n", "Team Totals:\n"); 

	
	for (int i =0; i<ArrTotalTeamSales.length; i++) {
		DecimalFormat formatterd = new DecimalFormat("$.00");
		String totalSales = formatterd.format(ArrTotalTeamSales[i]);
	pw.format("%-3s%1s%1s%15s%n", "", ArrTeam[i], "", totalSales);
	pw.println();
	}
	 
	
}

public static void ErrorReport() {
	
	errordetailheadings();
	
	 
	
}

public static void close() {
	pw.println();
	pw.println();
	pw.println();
	pw.println();
	pw.println();
	pw.println();
	pw.println();
	pw.println();
	
	heading();
	grandtotal();
	
	errorgrandTot();
	
}












}
