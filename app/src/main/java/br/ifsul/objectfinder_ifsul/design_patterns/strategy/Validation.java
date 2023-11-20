package br.ifsul.objectfinder_ifsul.design_patterns.strategy;

import br.ifsul.objectfinder_ifsul.design_patterns.strategy.interfaces.IValidation;

public abstract class Validation {
    public static boolean validate(String data, IValidation iValidation) {
        return iValidation.validate(data);
    }
}