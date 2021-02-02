package superfon.util;

import superfon.repository.query.SqlQuery;

public class PasswordGenerator {

    private static final int length = 6;

    private static String[] getAlphabet() {
        String[] alphas = new String[26];

        int j = 0;
        for (char i = 'a'; i <= 'z'; i++, j++)
            alphas[j] = String.valueOf(i);
        return alphas;
    }

    private static int[] getDigits() {
        int[] digits = new int[10];
        for (int i = 0; i < 10; i++)
            digits[i] = i;
        return digits;
    }

    private static int getRandomIndex(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }

    private String generatePassword() {
        StringBuilder password = new StringBuilder();
        int i = 0;
        int[] digits = getDigits();
        String[] alphas = getAlphabet();
        while (i < length) {
            password.append(i % 2 == 0 ? digits[getRandomIndex(9)] : alphas[getRandomIndex(25)]);
            i++;
        }
        return password.toString();
    }

    private boolean isPasswordValid(String password) {
        return new SqlQuery().getCustomerByPassword(password) == null;
    }

    public String getGeneratedPassword() {
        String generatedPassword = generatePassword();
        while (!isPasswordValid(generatedPassword))
            generatedPassword = generatePassword();
        return generatedPassword;
    }

}