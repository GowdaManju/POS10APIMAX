package com.Pos10Max.POS10APIMAX.Shared;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

        private final Random RANDOM=new SecureRandom();

        private String ALPHABET="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        private String ALPHABET1="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*?";


        public String generateUserId(int size) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
            }
            return builder.toString();
        }

        public String generateActivationKey(int size)
        {
            StringBuilder builder=new StringBuilder();
            for(int i=0;i<size;i++)
            {
                builder.append(ALPHABET1.charAt(RANDOM.nextInt(ALPHABET1.length())));
            }

            return builder.toString();

        }
        public ModelMapper getMapper()
        {
            return new ModelMapper();
        }





}
