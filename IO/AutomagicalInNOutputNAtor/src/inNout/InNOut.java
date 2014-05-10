package inNout;

import java.util.Scanner;

public class InNOut {
	
	private Scanner scant = new Scanner(System.in);
	private String input;
	private boolean validInput;
	
	public int PromptForInt(int min, int max, String message){
		int num = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				num = Integer.parseInt(input);
				if(num >= min && num <= max){
					validInput = true;
					return num;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only integers allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return num;
	}
	public int PromptForInt(String message){
		return PromptForInt(Integer.MIN_VALUE, Integer.MAX_VALUE, message);
	}
	public byte PromptForByte(byte min, byte max, String message){
		byte bits = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				bits = Byte.parseByte(input);
				if(bits >= min && bits <= max){
					validInput = true;
					return bits;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only bytes allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return bits;
	}
	public byte PromptForByte(String message){
		return PromptForByte(Byte.MIN_VALUE, Byte.MAX_VALUE, message);
	}
	public double PromptForDouble(double min, double max, String message){
		double dub = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				dub = Double.parseDouble(input);
				if(dub >= min && dub <= max){
					validInput = true;
					return dub;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only doubles allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return dub;
	}
	public double PromptForDouble(String message){
		return PromptForDouble(Double.MIN_VALUE, Double.MAX_VALUE, message);
	}
	public short PromptForShort(short min, short max, String message){
		short sh = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				sh = Short.parseShort(input);
				if(sh >= min && sh <= max){
					validInput = true;
					return sh;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only shorts allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return sh;
	}
	public short PromptForShort(String message){
		return PromptForShort(Short.MIN_VALUE, Short.MAX_VALUE, message);
	}
	public float PromptForFloat(float min, float max, String message){
		float flo = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				flo = Float.parseFloat(input);
				if(flo >= min && flo <= max){
					validInput = true;
					return flo;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only floats allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return flo;
	}
	public float PromptForFloat(String message){
		return PromptForFloat(Float.MIN_VALUE, Float.MAX_VALUE, message);
	}
	public long PromptForLong(long min, long max, String message){
		long looong = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				looong = Long.parseLong(input);
				if(looong >= min && looong <= max){
					validInput = true;
					return looong;
				}
				else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
			}
			catch(Exception e){System.out.println("Only longs allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return looong;
	}
	public long PromptForLong(String message){
		return PromptForLong(Long.MIN_VALUE, Long.MAX_VALUE, message);
	}
	public String PromptForString(String message){
		System.out.print(message);
		return scant.nextLine();
	}
	public char PromptForChar(char min, char max, String message){
		char a = 0;
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			if(input.length() != 1) System.out.println("Must enter precisely ONE character.");
			try{
				a = input.charAt(0);
				if(input.length() == 1){
					if(a >= min && a <= max){
						validInput = true;
						return a;
					}
					else System.out.println("Only values between " + min + " and " + max + " allowed.\n");
				}
			}
			catch(Exception e){System.out.println("Only characters allowed.");}
			System.out.print(message);
			input = scant.nextLine();
		}
		return a;
	}
	public char PromptForChar(String message){
		return PromptForChar(Character.MIN_VALUE, Character.MAX_VALUE, message);
	}
	public boolean PromptForBoolean(String message){
		validInput = false;
		System.out.print(message);
		input = scant.nextLine();
		while(!validInput){
			try{
				if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")
					|| input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")){
					validInput = true;
					if(input.equalsIgnoreCase("yes")) return true;
					else if(input.equalsIgnoreCase("no")) return false;
					else return Boolean.parseBoolean(input);
				}
				else System.out.println("Only 'true' or 'false' allowed.\n");
			}
			catch(Exception e){}
			System.out.print(message);
			input = scant.nextLine();
		}
		return Boolean.parseBoolean(input);
	}
	public int PromptForSelection(String[] options){//fix this one too?
		int selection = 0;
		validInput = false;
		do{
			for(int i=0; i<options.length; i++){
				System.out.println((i+1) + ") " + options[i]);
			}
			System.out.println("0) Exit");
			System.out.print("\nMake a selection: ");
			input = scant.nextLine();
			try{
				selection = Integer.parseInt(input);
				validInput = true;
			}
			catch(NumberFormatException e){System.out.println("Make a valid selection from the menu options.\n");}
		}
		while(!validInput || selection < 0 || selection > options.length);
		return selection;
	}
}