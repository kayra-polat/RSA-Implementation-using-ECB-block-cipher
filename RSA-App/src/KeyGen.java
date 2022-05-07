//----------------------------
// @author Kayra POLAT
// KeyGen.java
// Class implementation of Key Generation of RSA algorithm
//----------------------------

import java.math.BigInteger;
import java.util.Random;

public class KeyGen {
	private BigInteger e, d, p, totient;
	private BigInteger n, q;
	private final int iterations = 20;
	private final int specificBitNum = 1024;

	public KeyGen() {
		this.p = choosePrime(specificBitNum);
		this.q = choosePrime(specificBitNum);
		this.n = p.multiply(q);
		this.totient = Euler_Totient(p, q);
		this.e = calculate_e(n, totient);
		this.d = calculate_d(e, totient);
	}

	// This method is choose p and q values
	private BigInteger choosePrime(int bitnumber) {
		Random random;
		BigInteger choosenPrime;
		boolean prime_or_not = false;
		do {
			random = new Random();
			choosenPrime = BigInteger.probablePrime(bitnumber, random);
			prime_or_not = Fermats_Prime_Test(choosenPrime, iterations);
		} while (!prime_or_not); // if chosen prime number IS NOT a prime, then stay in loop

		return choosenPrime;

	}

	// This method check that gcd of the given values is 1 or not
	private boolean coprime_check(BigInteger i, BigInteger j) {
		BigInteger k = i.gcd(j);
		BigInteger one = BigInteger.ONE;
		int comparevalue = k.compareTo(one);

		if (comparevalue == 0)
			return true;
		else
			return false;

	}

	// Fermat's Primality Test Implementation
	private boolean Fermats_Prime_Test(BigInteger possible_prime, int iteration) {

		if (possible_prime.equals(BigInteger.ONE))
			return false;

		for (int i = 0; i < iteration; i++) {
			BigInteger a = chooseAppropriate_a(possible_prime);

			if (coprime_check(possible_prime, a)) {
				a = a.modPow(possible_prime.subtract(BigInteger.ONE), possible_prime);
				if (!a.equals(BigInteger.ONE))
					return false;
			}
		}

		return true;

	}

	// A helper method for chosen random "a" value for using it in Fermat's
	// Primality Test. It check the random "a" value is smaller than p (OR q) OR
	// not.
	private static BigInteger chooseAppropriate_a(BigInteger pvalue) {
		Random aRandom = new Random();

		while (true) {
			final BigInteger a = new BigInteger(pvalue.bitLength(), aRandom);
			// must have 1 <= a < n
			if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(pvalue) < 0) {
				return a;
			}
		}
	}

	private BigInteger calculate_e(BigInteger n, BigInteger totient_n) {

		Random rand = new Random();
		BigInteger possible_e = new BigInteger(1024, rand);
		boolean maybe_e = false;

		while (maybe_e == false) {
			if (totient_n.compareTo(possible_e) < 0 && possible_e.compareTo(BigInteger.ONE) < 0) {
				possible_e = new BigInteger(1024, rand);
				continue;
			}

			else if (!(possible_e.gcd(n).equals(BigInteger.ONE))) {
				possible_e = new BigInteger(1024, rand);
				continue;

			} else if (!(possible_e.gcd(totient_n).equals(BigInteger.ONE))) {
				possible_e = new BigInteger(1024, rand);
				continue;
			}

			maybe_e = true;
		}

		return possible_e;

	}

	private BigInteger Euler_Totient(BigInteger p, BigInteger q) {
		BigInteger totient;
		BigInteger a, b;

		a = p.subtract(BigInteger.ONE);
		b = q.subtract(BigInteger.ONE);

		totient = a.multiply(b);

		return totient;

	}

	public BigInteger calculate_d(BigInteger e, BigInteger totient_n) {
		BigInteger d;
		d = e.modInverse(totient_n);

		return d;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}
}
