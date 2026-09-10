package com.meubrecho.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) return true; // Deixe o @NotBlank lidar com campos vazios

        // Remove tudo que não é número
        String cleanCpf = cpf.replaceAll("\\D", "");

        if (cleanCpf.length() != 11 || cleanCpf.matches("(\\d)\\1{10}")) return false;

        try {
            int[] d = new int[11];
            for (int i = 0; i < 11; i++) d[i] = Character.getNumericValue(cleanCpf.charAt(i));

            int sum = 0;
            for (int i = 0; i < 9; i++) sum += d[i] * (10 - i);
            int digit1 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

            sum = 0;
            for (int i = 0; i < 10; i++) sum += d[i] * (11 - i);
            int digit2 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

            return (d[9] == digit1 && d[10] == digit2);
        } catch (Exception e) {
            return false;
        }
    }
}