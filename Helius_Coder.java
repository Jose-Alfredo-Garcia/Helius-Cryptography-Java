// TRY PUTTING IT AS A STANDARD OFFSET OF 5
/* the encoder will be approached in two ways
*	
*	1) The encoding runs on the ASCII table (not the table)
*		-> SPACE is 32
*		-> LETTERS are from 65 to 90 (A-Z)
*		-> NUMBERS are from 48 to 57
*		-> PUNCTUATIONS are from 40 to 47 [left side parenthesis to forward slash]
* 
*   2) The encoding runs strictly according to the encoding table
*/

import java.util.*;
public class Helius_Coder {
	
	//SHOULD JUST START THINGS, LET THE FUNCTIONS DO THE WORK
	public static void main(String[] args) {
		//getASCII();
		Menu();
		return;
	}	
	static void testFCN() {
		String phrase = "Hello World";
		for (int i = 0; i < phrase.length(); i++) {
			char character = phrase.charAt(i);
			int charToASCII = (int) character;
			System.out.println(character + " " + charToASCII);
		}
	}
	// ASSUMING CHARACTERS ARE RESTRICTED TO THESE 44 IN THAT ORDER
	public static ArrayList<Integer> getASCII() {
		//Initialize
		final ArrayList<Integer> listASCII = new ArrayList<Integer>();
		
		//Add UPPERLETTERS, NUMBERS, PUNCTUATIONS
		Collections.addAll(listASCII, 65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90);
		Collections.addAll(listASCII, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57);
		Collections.addAll(listASCII, 40, 41, 42, 43, 44, 45, 46, 47);
		//System.out.println(listASCII.size());
		//System.out.println(listASCII);
		return listASCII;
	}	
	// MENU TAKES IN STRING INPUT AND CHOICE OF FUNCTION
	static void Menu ()
	{
		Scanner input = new Scanner(System.in);
		int intOptionDecodeEncode = 0;
		String targetStringDecodeEncode = "";
		
		System.out.println("Please input string \n\t");
		targetStringDecodeEncode = input.nextLine();
		
		while ((intOptionDecodeEncode > 2) || (intOptionDecodeEncode < 1)  ) {
		System.out.println("\nPlease select an option: \n\t 1) Encode \n\t 2) Decode \n\t");
		intOptionDecodeEncode = input.nextInt();
		}
		
		switch (intOptionDecodeEncode)
		{
		case 1:
			encode(targetStringDecodeEncode);
			break; 
		case 2:
			decode(targetStringDecodeEncode);
			input.close();
			break;
		default:
			System.out.println("THIS SHOULD NOT BE HAPPENING");
			input.close();
			return;
		}
	}
	//ENCODES AND RETURNS A STRING
	public static String encode (String input)
	{ 	String output = "";
																		// input STRING to input CHARACTER ARRAYLIST
		input = input.toUpperCase();
		ArrayList<Character> inputToCharList = new ArrayList<Character>();
		for (char c : input.toCharArray()) {inputToCharList.add(c);}
																		// GET OFFSET VALUE
		ArrayList<Integer> encodeASCII = new ArrayList<Integer>(), charListToCODE = new ArrayList<Integer>();
		encodeASCII.addAll(getASCII());									//get localized contextual ASCII list to be rotated
		
		Scanner offsetScanner = new Scanner(System.in);					//initialize input for offset value
		System.out.println("\nPlease input desired offset value:\n\t");	//prompt user
		int offsetValue = offsetScanner.nextInt();							//obtained offset value
		offsetScanner.close();
		// ENCODE
		
		// for length of input STRING as CHAR -> translate char ARRAYLIST to integer ARRAYLIST
		for(int i = 0; i < inputToCharList.size(); i++) {				// H E L L O $ -> 72 69 76 76 79 99
		//H  E  L  L  O  $
			int inputASCIIEquivalent = (int)inputToCharList.get(i);
			if((inputASCIIEquivalent < 40 || inputASCIIEquivalent > 90) || (inputASCIIEquivalent >= 58 && inputASCIIEquivalent  <= 64 ) || (inputASCIIEquivalent == 32)) {
				//if invalid character, replace input index character with spacing
				charListToCODE.add(99);
			}
		else {	// if valid character, convert to ASCII integer
				charListToCODE.add(inputASCIIEquivalent);
			}
		}
		System.out.println(inputToCharList);
		System.out.println(charListToCODE);
																		//72 69 76 76 79 99	->	7 4 11 11 14 99	->	12 9 16 16 19 99
		for(int a = 0; a < charListToCODE.size(); a++) {				// 72 69 76 76 79 99 ->	7 4 11 11 14 99	->	12 9 16 16 19 99
			// replace ASCII value in inputToCharList with INDEX value with respect to original ASCII + offset
			if(charListToCODE.get(a) != 99) {							// if not a spacing value, assigned a CODE equivalent value + offset value (72 -> 12)
			int encodeASCIIEquivalent = (encodeASCII.indexOf(charListToCODE.get(a))) + offsetValue;
			charListToCODE.set(a, encodeASCIIEquivalent);				// that value replaces the existing value in that position (input[0] = 12)
				}
			}
		System.out.println(charListToCODE);
		output = Character.toString(encodeASCII.get(offsetValue-1));				//start output with offset value E
		System.out.println(output);
		for(int b = 0; b < charListToCODE.size(); b++) {				//12 9 16 16 19 99 -> E H E L L O _ 
			// replace ASCII value in inputToCharList with INDEX value with respect to original ASCII
			if(charListToCODE.get(b) != 99) {							// if not a spacing value, retrieve 
			output = output + Character.toString(encodeASCII.get(charListToCODE.get(b) -1));
				}
			else {output = output + (char)32;}							// if spacing value add space
			}
		
		System.out.println("\nENCODE SUCCESS: " + input + " -> " + output);
		return output;
	}
	
	//DECODES AND RETURNS A STRING
	public static String decode (String input)
	{
		String output = "";
																		// input STRING to input CHARACTER ARRAYLIST
		input = input.toUpperCase();
		ArrayList<Character> inputToCharList = new ArrayList<Character>();
		for (char c : input.toCharArray()) {inputToCharList.add(c);}
																		// GET OFFSET VALUE
		ArrayList<Integer> encodeASCII = new ArrayList<Integer>();
		encodeASCII.addAll(getASCII());									//get localized contextual ASCII list
		
		//now that your input has been split up, and your CODE tables have been initialized
		//get first letter of encoded letter & retrieve index position within CODEX
		int offsetValue = encodeASCII.indexOf((int)inputToCharList.get(0));		
		
		//use encoded ASCII and subtract by value of offset value
		for(int a = 1; a < inputToCharList.size(); a++) {

			if((int)inputToCharList.get(a) != 32) {
			int codeIndex = encodeASCII.indexOf((int)inputToCharList.get(a))- offsetValue;
			int codeEquivalent = encodeASCII.get(codeIndex);
			char charEquivalent = (char)codeEquivalent;
			output = output + charEquivalent;
			}
			else {output = output + " ";}
		}
		System.out.println("\nDECODE SUCCESS: " + input + " -> " + output);
		return output;
	}
}
