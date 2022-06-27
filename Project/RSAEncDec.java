//(2) A program that inputs a public key-private key pair, the ascii value of the character to encrypt and prints the cipher text as well as the decrypted plaintext 
 
import java.util.*; 
import java.math.BigInteger; 
 
class RSAEncDec{ 
 
  public static void main(String[] args){ 
 
    BigInteger bigB_pubKey = new BigInteger(args[0]);     
	  BigInteger bigB_prvKey = new BigInteger(args[1]);     
	  BigInteger bigB_n = new BigInteger(args[2]); 
 
    int asciiVal = Integer.parseInt(args[3]);    
 
    /* encrypting an ASCII integer value using the public key and decrypting the cipher value using the private key and extracting the ASCII value back */ 
 
    BigInteger bigB_val = new BigInteger(""+asciiVal);     
  	BigInteger bigB_cipherVal =  bigB_val.modPow(bigB_pubKey, bigB_n); 
 
    System.out.println("ciphertext: "+bigB_cipherVal); 
 
    BigInteger bigB_plainVal = bigB_cipherVal.modPow(bigB_prvKey, bigB_n);     
    int plainVal = bigB_plainVal.intValue(); 
 
    System.out.println("plaintext:  "+plainVal);   
 
   } 
} 