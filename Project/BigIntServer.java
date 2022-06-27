import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.*;

class BigIntServer {
  private RSAKeyGen rsaKeyGen;

  // dinh nghia ham decryption
  private char decryption(BigInteger msg, BigInteger bigB_pubKey, BigInteger bigB_n) 
  {
    BigInteger msgDec = msg.modPow(rsaKeyGen.getBigB_prvKey(), rsaKeyGen.getBigB_n());
    msgDec = msgDec.modPow(bigB_pubKey, bigB_n);
    return ((char) msgDec.intValue());
  }

  public BigIntServer(String port) 
  {
    //Tao khoa
    rsaKeyGen = new RSAKeyGen(""+ (int) (Math.random() * 1000));

    Scanner sc = new Scanner(System.in);

    // nhap public key tu Client
    System.out.print("Input Public key from client: ");
    String pub  = sc.nextLine();

    // nhap gia tri n cua public key tu Client
    System.out.print("Input n value from client: ");
    String n = sc.nextLine();
    try 
    {
      // Tạo port theo parameter tu nguoi dung
      ServerSocket serv = new ServerSocket(Integer.parseInt(port));

      // cap phep dang nhap
      Socket socket = serv.accept();

      // nhan du lieu duoc chuyen giao tu nguoi dung
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

      //dieu kien dung
      BigInteger big_check = new BigInteger("-1");

      //dinh nghia thong diep rong,
      String msg = "";
      // doc tung tu cac character tu nguoi dung duoi dang cac ban ma hoa
      while (true) 
      {
        BigInteger bigInt = (BigInteger) ois.readObject();
        System.out.println("ciphertext characters received: " + bigInt);

        // ket thuc qua trinh doc tin
        if (bigInt.equals(big_check))
            break;

        // giai ma lan luot cac character dua vao public key va n value
        char temp_s = decryption(bigInt, new BigInteger(pub), new BigInteger(n));
        System.out.println("ciphertext characters decryption is: " + temp_s);

        // tong hop cac character thanh mot message cuoi 
        msg = msg + temp_s;
      }
      System.out.println("Successfully decoding the message is: " + msg);
      socket.close();
      serv.close();
    } 
    catch (Exception e) { e.printStackTrace();}
  }
  public static void main(String[] args) {
      new BigIntServer(args[0]);
  }

} 
 
