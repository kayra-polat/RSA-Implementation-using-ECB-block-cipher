//----------------------------
// @author Kayra POLAT
// Decryption.java
// Class implementation of Decryption of ECB block mode
//----------------------------

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Decryption {
	private BigInteger decodedmsg;
	private BigInteger d, n; // Private Key
	private ArrayList<BigInteger> splittedCiphertext = new ArrayList<BigInteger>();
	private String real_plaintext;
	private int stealing;

	public Decryption(BigInteger d, BigInteger n, ArrayList<BigInteger> encryptedblocks, int stealing) {
		this.d = d;
		this.n = n;
		this.splittedCiphertext = encryptedblocks;
		this.stealing = stealing;
		this.real_plaintext = decrypt_with_ecb(encryptedblocks);
	}

	// Convert method to convert ascii value to it's character
	private String ascii_to_string(String msg) {

		String output = "";
		int i = 0;
		while (i < msg.length()) {
			int temp = Integer.parseInt(msg.substring(i, i + 2));
			char ch = (char) temp; // take actual character with basic casting
			output = output + ch;
			i = i + 2;
		}
		return output;

	}

	// RSA Decryption with ECB block mode operation
	private String decrypt_with_ecb(ArrayList<BigInteger> ciphertext) {

		String decrypted = "";
		String plaintext = "";
		String tempString = "";

		if (stealing == 1) {
			Collections.swap(ciphertext, ciphertext.size() - 2, ciphertext.size() - 1);
			// Temporary decrypt last block for take last 8-bit and give this 8-bit to
			// penultimate block
			// This is done to correctly decrypt the penultimate block.
			decrypted = ciphertext.get(ciphertext.size() - 1).modPow(d, n).toString();

			tempString = decrypted.substring(decrypted.length() - 8);

			String second_last = ciphertext.get(ciphertext.size() - 2).toString();
			second_last = second_last + tempString;
			BigInteger temp = new BigInteger(second_last);
			ciphertext.set(ciphertext.size() - 2, temp);
		}

		// Decrypt ciphertext block by block
		for (BigInteger bigInteger : ciphertext) {

			decrypted = bigInteger.modPow(d, n).toString();

			// Check ciphertext stealing is applied AND are we on the last block
			if (stealing == 1 && (ciphertext.get(ciphertext.size() - 1) == bigInteger)) {

				// take first TWO because other parts is not necessary
				decrypted = decrypted.substring(0, 2);
			}

			plaintext = plaintext + decrypted;
		}

		plaintext = ascii_to_string(plaintext); // Convert plaintext ascii to actual plaintext

		return plaintext;

	}

	public BigInteger getDecodedmsg() {
		return decodedmsg;
	}

	public void setDecodedmsg(BigInteger decodedmsg) {
		this.decodedmsg = decodedmsg;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public ArrayList<BigInteger> getSplittedCiphertext() {
		return splittedCiphertext;
	}

	public void setSplittedCiphertext(ArrayList<BigInteger> splittedCiphertext) {
		this.splittedCiphertext = splittedCiphertext;
	}

	public String getReal_plaintext() {
		return real_plaintext;
	}

	public void setReal_plaintext(String real_plaintext) {
		this.real_plaintext = real_plaintext;
	}
}
