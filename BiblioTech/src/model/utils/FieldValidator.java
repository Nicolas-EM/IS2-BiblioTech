package model.utils;

import java.util.regex.Pattern;

public class FieldValidator {
	private static boolean activar = true;
	
	private final static char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
	
	public static void desactivar() {
		activar = false;
	}
	
	public static void activar() {
		activar = true;
	}
	
	public static boolean isValidDNI(String dni) {
		if(dni.contentEquals("user") || dni.contentEquals("biblio") || dni.contentEquals("admin"))
			return true;
		if(activar) {
			if(dni.length() != 9)
				return false;
			else {
				try {
					if(dni.charAt(0) == 'Y') {
						dni = "1" + dni.substring(1, dni.length());
					}
					int numDNI = Integer.parseInt(dni.substring(0, dni.length() - 1));		
					char finalLetter = dni.charAt(dni.length() - 1);
					
					return finalLetter == letras[numDNI % 23];
				}
				catch (Exception e){
					return false;
				}
			}
		}
		else
			return true;
	}
	
	public static boolean isValidEmail(String email) {
		if(activar) {
			String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			return Pattern.compile(regexPattern).matcher(email).matches();
		}
		else
			return true;
	}
	
	public static boolean isValidISBN(String isbnString) {
		if(isParsableToInt(isbnString)) {
			return isValidISBN(Integer.parseInt(isbnString));
		}
		return false;
	}
	
	public static boolean isValidISBN(int isbn) {
		return true;
		/*int i = 1, count = 0;
		
		while(isbn > 0 && i < 11) {
			count += i * (isbn % 10);
			isbn /= 10;
			i++;
		}
		if(i == 11 && count % 11 == 0)
			return true;
		return false;*/
	}
	
	public static boolean isParsableToInt(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    } catch (final NumberFormatException e) {
	        return false;
	    }
	}
}
