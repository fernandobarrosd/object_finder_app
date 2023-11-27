package br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations;

import br.ifsul.objectfinder_ifsul.design_patterns.strategy.interfaces.IValidation;

public class ValidationPassword implements IValidation {
    @Override
    public boolean validate(String data) {
        return data.length() <= 12;
    }
}
