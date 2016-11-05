package com.permanentMarker.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 13 Oct 2016
 */
public class EncryptedPassword {

    int iterations;
    String salt;
    String hash;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
