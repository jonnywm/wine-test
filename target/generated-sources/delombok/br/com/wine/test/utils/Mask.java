package br.com.wine.test.utils;

/**
 *
 * @author jonny
 */
public class Mask {
    
    public static String maskCep(String cep) {
        return mask(cep, "##.###-###");
    }
    public static String unmaskCep(String cep){
        return cep.replace(".", "").replace("-", "");
    }
    public static String mask(String value, String mask) {
        for (int i = 0; i < value.length(); i++) {
            mask = mask.replaceFirst("#", value.substring(i, i + 1));
        }
        return mask;
    }
}
