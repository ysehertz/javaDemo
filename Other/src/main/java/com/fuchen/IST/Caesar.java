package com.fuchen.IST;

/**
 * ClassName: Caesar
 * Package: com.fuchen.IST
 * Description:
 *
 * @author gomkiri
 * @version 1.0
 */
public class Caesar {

    public String encrypt(String plaintext) {
        return encrypt(plaintext, 3);
    }

    public String encrypt(String plaintext, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                char encryptedChar = (char) ((ch - base + shift) % 26 + base);
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        return encrypt(ciphertext, 26 - 3);
    }
    public String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - (shift % 26));
    }
}
