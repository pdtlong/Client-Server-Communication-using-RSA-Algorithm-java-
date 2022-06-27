import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.*;

class BigIntClient {
  private RSAKeyGen rsaKeyGen;

  private BigInteger encryption(BigInteger charEnc, BigInteger bigB_pubKey, BigInteger bigB_n)
  {

    charEnc = charEnc.modPow(rsaKeyGen.getBigB_prvKey(), rsaKeyGen.getBigB_n());
    System.out.println("Encryption values after that: "+ charEnc );
    return charEnc.modPow(bigB_pubKey, bigB_n);
  }

  public BigIntClient(String host, String port) 
  {
    // Tao khoa 
    rsaKeyGen = new RSAKeyGen(""+ (int) (Math.random() * 1000));
    try
    {
      String fullname; 
      Scanner sc = new Scanner(System.in);
      // nhap input plaintext( ex: Nguyen Van A)
      System.out.println("Input your fullname: "); 
      fullname = sc.nextLine(); 

      // Dinh nghia IP cho local host
      InetAddress serverHost = InetAddress.getByName(host);
      int serverPortNum = Integer.parseInt(port);

      // nhap public key tu server
      System.out.print("Input Public key from server: ");
      String public_key  = sc.nextLine();

      // nhap gia tri n cua public key tu server
      System.out.print("Input n value from server: ");
      String n = sc.nextLine();

      Socket clientSocket = new Socket(serverHost, serverPortNum);
      ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
      for (int i = 0; i < fullname.length(); i++) 
      {
          //chuyen mot ky tu trong massage thanh format BigInteger
          BigInteger char_bigint = new BigInteger(""+((int) fullname.charAt(i)));
          System.out.println("Big Integer values of "+ fullname.charAt(i) + " :" + char_bigint);

          //chuyen doan encryption tu ham encryption() o tren da duoc dinh nghia sang cho server duoi dang tung ky tu
          oos.writeObject(encryption(char_bigint, new BigInteger(public_key), new BigInteger(n)));
          oos.flush();
          if (i == (fullname.length() -1) )
            System.out.println("The encryption of message has been sent !");
      }

      // end
      BigInteger bigInt = new BigInteger("-1");
      oos.writeObject(bigInt);
      oos.flush();

      clientSocket.close();
    } 
    catch (Exception e) {e.printStackTrace();}
  }

  public static void main(String[] args) 
  {
      new BigIntClient(args[0], args[1]);
  }

} 