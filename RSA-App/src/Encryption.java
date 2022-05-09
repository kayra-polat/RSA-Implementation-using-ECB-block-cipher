//----------------------------
// @author Kayra POLAT
// Encryption.java
// Class implementation of Encryption with ECB block mode
//----------------------------

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class Encryption {
	private BigInteger e, n; // Public key
	private String message; // Plaintext
	private BigInteger encodedmsg;
	private ArrayList<BigInteger> ciphertexts_blocks = new ArrayList<BigInteger>(); // Store encrypted blocks in
																					// arraylist
	private String base64EncodedString; // Encoded string in base64 format
	private int ciphertext_stealing_or_not = 0;

	public Encryption(BigInteger e, BigInteger n, String message) {
		this.e = e;
		this.n = n;
		this.message = message;
		this.encodedmsg = encrypt_with_ecb(message);
	}

	// Private inner method to take string and convert it to appropriate ASCII value
	private BigInteger stringtoascii(String block) {
		// Change Uppercase of all letter because Uppercase letter have fixed 8-bit size
		// ( 2 digits NOT 3 digits)
		block = block.toUpperCase(Locale.ENGLISH);
		String convertion_to_ascii = "";

		for (int i = 0; i < block.length(); i++) {
			int ascii = (int) block.charAt(i); // Return char to its ascii value with basic casting
			convertion_to_ascii = convertion_to_ascii + ascii;
		}
		BigInteger bigint_block = new BigInteger(convertion_to_ascii);
		return bigint_block;

	}

	// RSA encryption with ECB block mode
	private BigInteger encrypt_with_ecb(String message) {

		String blockString = "";
		String ciphertextString = "";
		String stealing_helper = "";
		String cipherblockString = "";
		BigInteger asciiblock = null;
		BigInteger cipherblock;
		String swap1 = "";
		String swap2 = "";

		if (message.length() % 2 != 0)
			ciphertext_stealing_or_not = 1;

		int index = 0;
		while (index < message.length()) {

			// This if block used when we need ciphertext stealing. It takes the last 8-bit
			// of the penultimate encrypted block and gives it to the last block and
			// encrypts the last block in this way.
			if (index == message.length() - 1 && ciphertext_stealing_or_not == 1) {
				stealing_helper = cipherblockString.substring(cipherblockString.length() - 8);
				blockString = message.substring(message.length() - 1);
				asciiblock = stringtoascii(blockString);
				String asciiBlock_string = asciiblock.toString();
				asciiBlock_string = asciiBlock_string + stealing_helper;
				BigInteger temp = new BigInteger(asciiBlock_string);
				asciiblock = temp;

				cipherblock = asciiblock.modPow(this.e, this.n); // RSA encryption

				ciphertexts_blocks.add(cipherblock);

				cipherblockString = cipherblock.toString();
				swap2 = cipherblockString;

				index += 2;
				break;
			}

			blockString = message.substring(index, index + 2);
			asciiblock = stringtoascii(blockString);

			cipherblock = asciiblock.modPow(this.e, this.n); // RSA encryption

			// Deletes the last 8-bits of the penultimate block if there is ciphertext
			// stealing.
			if (index == message.length() - 3 && ciphertext_stealing_or_not == 1) {

				cipherblockString = cipherblock.toString();

				String temporary = cipherblock.toString().substring(0, cipherblock.toString().length() - 8);
				BigInteger tempblock = new BigInteger(temporary);

				ciphertexts_blocks.add(tempblock);
				swap1 = temporary;
				index += 2;
				continue;
			}

			ciphertexts_blocks.add(cipherblock);

			cipherblockString = cipherblock.toString();
			ciphertextString = ciphertextString + cipherblockString;

			index += 2;
		}

		if (ciphertext_stealing_or_not == 1) {
			// SWAP LAST TWO BLOCK
			ciphertextString = ciphertextString + swap2;
			ciphertextString = ciphertextString + swap1;

			// Swap after ciphertext stealing
			Collections.swap(ciphertexts_blocks, ciphertexts_blocks.size() - 2, ciphertexts_blocks.size() - 1);
		}

		BigInteger encryptedtext = new BigInteger(ciphertextString);
		byte[] encryptedM = encryptedtext.toByteArray();
		this.base64EncodedString = Base64.getEncoder().encodeToString(encryptedM);

		return encryptedtext;

	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigInteger getEncodedmsg() {
		return encodedmsg;
	}

	public void setEncodedmsg(BigInteger encodedmsg) {
		this.encodedmsg = encodedmsg;
	}

	public ArrayList<BigInteger> getCiphertexts_blocks() {
		return ciphertexts_blocks;
	}

	public void setCiphertexts_blocks(ArrayList<BigInteger> ciphertexts_blocks) {
		this.ciphertexts_blocks = ciphertexts_blocks;
	}

	public String getBase64EncodedString() {
		return base64EncodedString;
	}

	public void setBase64EncodedString(String base64EncodedString) {
		this.base64EncodedString = base64EncodedString;
	}

	public int getCiphertext_stealing_or_not() {
		return ciphertext_stealing_or_not;
	}

	public void setCiphertext_stealing_or_not(int ciphertext_stealing_or_not) {
		this.ciphertext_stealing_or_not = ciphertext_stealing_or_not;
	}
}
