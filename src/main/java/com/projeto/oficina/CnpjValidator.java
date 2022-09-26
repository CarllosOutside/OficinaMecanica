package com.projeto.oficina;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CnpjValidator implements ConstraintValidator<Cpf, String>{
	// 5,4,3,2,9,8,7,6,5,4,3 e 2 
	private final int[] PESO_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4,3,2 }; //LENGTH = 13

	@Override
	public boolean isValid(String cnpj, ConstraintValidatorContext context) {
		
		String cnpjSomenteDigitos = cnpj.replaceAll("\\D", "");
		
		if ((cnpjSomenteDigitos == null) || (cnpjSomenteDigitos.length() != 14) || cnpjSomenteDigitos.equals("00000000000000")
				|| cnpjSomenteDigitos.equals("11111111111111") || cnpjSomenteDigitos.equals("22222222222222")
				|| cnpjSomenteDigitos.equals("33333333333333") || cnpjSomenteDigitos.equals("44444444444444")
				|| cnpjSomenteDigitos.equals("55555555555555") || cnpjSomenteDigitos.equals("66666666666666")
				|| cnpjSomenteDigitos.equals("77777777777777") || cnpjSomenteDigitos.equals("88888888888888")
				|| cnpjSomenteDigitos.equals("99999999999999")) {
			return false;
		}
		//calculos dos verificadores
		Integer digito1 = calcularDigito(cnpjSomenteDigitos.substring(0, 12), PESO_CNPJ);
		Integer digito2 = calcularDigito(cnpjSomenteDigitos.substring(0, 13) + digito1, PESO_CNPJ);
		//verifica se digitos VERIFICAFORES batem COM OS CALULADOS
		return cnpjSomenteDigitos.equals(cnpjSomenteDigitos.substring(0, 12) + digito1.toString() + digito2.toString());
	}
	
	private int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) { //COMEÇA NO ULTIMO DIGITO
			//soma com pesos iterativa
			digito = Integer.parseInt(str.substring(indice, indice + 1)); //RETORNA SRT[INDICE]. STR[11] = 12º DIGITO
			soma += digito * peso[peso.length - str.length() + indice]; //DIGITO 1 -> PESOLENGTH-STRLENGTH = 1 --- DIGITO 2 -> 0
			//PESO[1+11] = 2
		}
		soma = 11 - soma % 11; //SE O RESTO FOR MENOR QUE 2, 11-R>9
		return soma > 9 ? 0 : soma; //SE O DIGITO É >9, CONSIDERA-SE 0.
	}

}
