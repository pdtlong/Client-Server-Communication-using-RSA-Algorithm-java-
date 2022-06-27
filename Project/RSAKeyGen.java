import java.util.*;
import java.math.BigInteger;

class RSAKeyGen {
    private BigInteger bigB_n;
    private BigInteger bigB_pubKey;
    private BigInteger bigB_prvKey;

    public RSAKeyGen(String key) {
        /* Creating two random number generators, each with a different seed */
        Random rand1 = new Random(System.currentTimeMillis());
        Random rand2 = new Random(System.currentTimeMillis() * 10);
        int pubKey = Integer.parseInt(key); /* public key is at least
              a certain value input by the user */
        /* returns a BigInteger that is not prime with probability less than 2^(-100)
         */
        BigInteger bigB_p = BigInteger.probablePrime(32, rand1);
        BigInteger bigB_q = BigInteger.probablePrime(32, rand2);

        this.bigB_n = bigB_p.multiply(bigB_q);
        BigInteger bigB_p_1 = bigB_p.subtract(new BigInteger("1")); //p-1
        BigInteger bigB_q_1 = bigB_q.subtract(new BigInteger("1")); //q-1
        BigInteger bigB_p_1_q_1 = bigB_p_1.multiply(bigB_q_1); // (p-1)*(q-1)
        // generating the correct public pubKey
        while (true) {
            BigInteger BigB_GCD = bigB_p_1_q_1.gcd(new BigInteger("" + pubKey));
            if (BigB_GCD.equals(BigInteger.ONE)) {
                break;
            }
            pubKey++;
        }
        this.bigB_pubKey = new BigInteger("" + pubKey);  //e
        this.bigB_prvKey = bigB_pubKey.modInverse(bigB_p_1_q_1); //d

        System.out.println(" public key : " + bigB_pubKey + " , " + bigB_n);
        System.out.println(" private key: " + bigB_prvKey + " , " + bigB_n);
    }

    public BigInteger getBigB_n() {
        return bigB_n;
    }

    public BigInteger getBigB_pubKey() {
        return bigB_pubKey;
    }

    public BigInteger getBigB_prvKey() {
        return bigB_prvKey;
    }
}