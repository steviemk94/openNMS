package opennms.main;

import java.io.FileNotFoundException;
import java.util.Scanner;

import opennms.filter.SNMPTrapFilter;

public class MainClass {

	public static void main (String [] args) throws FileNotFoundException {
		SNMPTrapFilter filter = new SNMPTrapFilter(args[0]);
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please enter OID:");
		String oid = "";
		oid = in.next();
		
		if(oid.isEmpty()) {
			System.out.println("Missing OID, please restart");
		} else {
			System.out.printf("Processing %s", oid);
			
			boolean isValid = filter.isOIDValid(oid);
			
			System.out.printf("%nResults for %s; IsValid: %b", oid,isValid);
		}
		
		
		in.close();
	}
}
