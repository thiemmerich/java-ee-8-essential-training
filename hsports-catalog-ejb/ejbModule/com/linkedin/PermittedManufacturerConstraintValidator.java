package com.linkedin;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PermittedManufacturerConstraintValidator implements ConstraintValidator<PermitterManufacturer, String> {

	private static String[] permittedManufacturers = {"CompanyA" , "CompanyB"};

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.asList(permittedManufacturers).contains(value);
	}

}
