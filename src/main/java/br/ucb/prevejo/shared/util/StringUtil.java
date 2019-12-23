package br.ucb.prevejo.shared.util;

import java.text.Normalizer;
import java.util.Base64;

public class StringUtil {

    /**
     * Tenta realizar o parse de uma string em double.
     * @param str String.
     * @return Double; null caso a string nÃ£o possua
     */
    public static Double tryDoubleParse(String str) {
        Double d = null;

        if (str != null && !str.isEmpty()) {
            try {
                d = Double.parseDouble(str);
            } catch(NumberFormatException e) {}
        }

        return d;
    }

    /**
     * Gera uma string contendo dÃ­gitos aletÃ³rios.
     * @param maxDigitos Quantidade de dÃ­gitos.
     * @return String.
     */
    public static String gerarRandomStr(int maxDigitos) {
        String resultado = "";

        int seed;

        for (int i = 0; i < 6; i++) {
            seed = (int) (Math.random() * 123);
            while (!((seed >= 48 && seed <= 57) || (seed >= 97 && seed <= 122))) {
                seed = (int) (Math.random() * 100);
            }
            resultado += (char) seed;
        }

        return resultado;
    }

    /**
     * Obtem os dÃ­gitos dentro de uma string.
     * @param str String.
     * @return String contendo somente os dÃ­gitos numÃ©ricos; null caso a string seja nula.
     */
    public static String getDigits(String str) {
        if (str != null) {
            char[] digitos = new char[str.length()];
            int len = 0;

            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);

                if (Character.isDigit(c)) {
                    digitos[len++] = c;
                }
            }

            return new String(digitos, 0, len);
        }

        return null;
    }

    /**
     * Remove a acentuaÃ§Ã£o de um campo.
     * @param campo Campo.
     * @return Campo sem acentuaÃ§Ã£o.
     */
    public static String removeAcentuacao(String campo) {
        return Normalizer.normalize(campo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").trim();
    }

    /**
     * Transforma um array de bytes em uma Data URI.
     * @param bytes Bytes.
     * @param mimeType Mime type dos dados.
     * @return String contendo a data uri.
     */
    public static String toDataURI(byte[] bytes, String mimeType) {
        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Verifica se dois nomes de humanos correspondem a ao outro.
     * @param nome1 Nome 1.
     * @param nome2 Nome 2.
     * @return Nomes iguais.
     */
    public static boolean equalsNomes(String nome1, String nome2) {
        nome1 = removeAcentuacao(nome1).replace(" ", "").toUpperCase();
        nome2 = removeAcentuacao(nome2).replace(" ", "").toUpperCase();

        return nome1.equals(nome2);
    }

    /**
     * Verifica se uma string Ã© igual a pelo menos uma em um array.
     * @param str String.
     * @param array Array de strings.
     * @return True, array contÃ©m a dada string; false, caso contrÃ¡rio.
     */
    public static boolean isIn(String str, String[] array) {
        boolean in = false;

        for (String string : array) {
            if (str.equals(string)) {
                in = true;
                break;
            }
        }

        return in;
    }

    /**
     * Obtem o objeto cujo representaÃ§Ã£o em string confere com uma dada string.
     * @param str String.
     * @param objects Conjunto de objetos.
     * @return
     */
    public static Object valueOf(String str, Object[] objects) {
        Object obj = null;

        if (str != null && !str.isEmpty()) {
            str = removeAcentuacao(str.toUpperCase().trim().replace(" ", "_"));

            for (Object o : objects) {
                if (o.toString().contains(str)) {
                    obj = o;
                    break;
                }
            }
        }

        return obj;
    }

    /**
     * Diz se uma string Ã© nula ou vazia.
     * @param str String.
     * @return true, se a string Ã© nula ou vazia.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return isEmpty(obj.toString());
    }

}
