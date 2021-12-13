package com.ph.thread.threadSpecificStorage;


import java.security.SecureRandom;

public class ThreadSpecificSecureRandom {

    public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<SecureRandom>(){
        protected SecureRandom initialValue(){
            SecureRandom secureRandom;
            try {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            }catch (Exception e){
                e.printStackTrace();
                secureRandom = new SecureRandom();
            }
            return secureRandom;
        }
    };

    public int nextInt(int upperBound){
        SecureRandom secureRandom = SECURE_RANDOM.get();
        return secureRandom.nextInt(upperBound);
    }

    public void setSeed(long seed){
        SecureRandom secureRandom = SECURE_RANDOM.get();
        secureRandom.setSeed(seed);
    }
}
