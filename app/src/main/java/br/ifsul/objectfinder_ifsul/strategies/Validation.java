package br.ifsul.objectfinder_ifsul.strategies;

import br.ifsul.objectfinder_ifsul.strategies.interfaces.IValidation;

public abstract class Validation {
    public static boolean validate(String data, IValidation iValidation) {
        return iValidation.validate(data);
    }
}