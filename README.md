# 🔐 RSA-Implementation-using-ECB-block-cipher

## Introduction
- This program is a program that encrypts the text entered by the user using the RSA algorithm and, if the user wishes, decrypts the encrypted data using the RSA algorithm and displays it to the user.
- This program was written in [Eclipse](https://www.eclipse.org/) using [JAVA](https://www.java.com/tr/).
- The frontend application was written using Java's [Swing](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html) and [awt](https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html) library.
- Normally ciphertext stealing is not a good practice due to the nature of the RSA Algorithm, but this program shows how the ciphertext stealing mentality can be somehow adapted to the RSA Algorithm.
- Descriptions of methods and some important implementations are included in the Java files in the RSA-App file.

## Explanation of the program
- Object oriented logic has been used extensively while writing the program. Along with the RSA_GUI class, there are 3 more classes.
  ### Key Gen.java
  - The Key Generation class is a class that allows us to create the Public-Private key pairs of the RSA Algorithm. This class, which calculates key pairs without using libraries like java.security, does all the important calculations required by the RSA algorithm.
  - When creating public and private keys, the goal is to have e, d, and n values at least 1024-bits long. Therefore, [BigInteger](https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html) data structure, which is a data structure provided by Java, is a data structure used in the whole program in general.
  - Our goal is to determine e, d and its variables. Because Public Key(e,n) and Private Key(d,n) use these values. In order to determine these values, the steps below must be followed:
    1. Pick two prime numbers => p and q
        - We apply Fermat's primality Test to select them.
        - **Fermat's primality Test**
            1. Choose random "a" (a < p)
            2. Apply gcd(p,a) => IF equals 1 move next step
            3. Apply Fermat's Little Theorem
                ```bash
                a^p-1 mod p = 1
                ```
                => IF is true "p" is probable prime
            4. The first four steps are repeated 20 times. The latest p value is 99.9% prime.
            5. The Fermat Primality Test is also applied for "q".
    2. Compute "n".
        ```bash
        n = p x q
        ```
    3. Apply Euler Totient Funtion
        ```bash
        ϕ(n) = ϕ(p).ϕ(q)
             = (p-1).(q-1)
        ```
    4. Choose "e" such that:
        - 1 < e < ϕ(n)
        - co-prime with n
        - co-prime with ϕ(n)
    5. Choose "d" such that:
        ```bash
        d x e (mod ϕ(n)) = 1
        ```
        - Basically, calculate Modular multiplicative inverse of "e"
 
  ### Encrpytion.java
  - Implemented by the combination of the RSA encryption with the ECB block cipher, this class is basically based on the formula:(Enryption use ***Public Key(e, n)***)
    ```bash
    C(ciphertext) = M(message)^e mod n
    ```
  - The ECB block cipher is a mode operation that helps to encrypt a plaintext by dividing it into equal parts (16-bit for this program), as shown in the diagram.
  
  ![Ecb_encryption](https://user-images.githubusercontent.com/75734949/167267734-ac074672-033e-4bb8-bb44-2efdaf7b6314.png)
  - When encrypting plaintext, the ASCII value of each character is calculated. Since the ASCII value of each character is 8-bit in length, 2 characters are sent to each block in ECB.
  - Because the block size must be 16-bit, [Ciphertext Stealing](https://en.wikipedia.org/wiki/Ciphertext_stealing) is used when necessary.
  
  ### Decryption.java
  - Implemented by the combination of the RSA decryption with the ECB block cipher, this class is basically based on the formula:(Decryption use ***Private Key(d, n)***)
    ```bash
    M(message) = C(ciphertext)^d mod n
    ```
  - Because ECB is used, encrypted data becomes very long BigIntegers. We need to keep every block we encrypt in an ArrayList because it is very difficult for us to correctly split our ciphertext while decrypting. This class decrypts and concatinates the encrypted blocks stored in the arraylist one by one. This is how the original plaintext is accessed.
  - By decrypting each 16-bit block using RSA decryption, we reach the original state of the blocks.
  - Since there are ASCII values of the decrypted characters in each block, we also return them to their original character states.

  ### RSA_GUI.java
  - The application works with a user interface.
  ![RSA_GUI](https://user-images.githubusercontent.com/75734949/167268759-30293abc-e1ff-434c-ac5d-9441a53d9db4.png)
  - Before encrypting a data, we can generate different key-pairs by pressing the "Key Generation" button. After choosing one of these key-pairs, the data to be encrypted is written and the encrypt button is pressed. Encrypted data is reflected to the user in [Base-64](https://en.wikipedia.org/wiki/Base64)
  - When the Decrypt button is pressed, the encrypted data returns to its original state and is displayed to the user.
  - If the user wants to encrypt without typing a text or selecting a key, a pop-up message is displayed on the screen. 
  
  ![Pop-up GUI](https://user-images.githubusercontent.com/75734949/167269097-006e36ec-642a-48df-beb5-8d30d784ded3.png)
  
  - If the user encrypts and decrypts a data with the public key he/she chooses, and then tries to decrypt it with another private key, not the private key of that public key, a warning message as in the image below is displayed to the user.
 
![PrivateKey-PopUp](https://user-images.githubusercontent.com/75734949/167662552-d5ae9123-ce9c-45db-ba62-1491b922c779.png)

        
        
        
        
