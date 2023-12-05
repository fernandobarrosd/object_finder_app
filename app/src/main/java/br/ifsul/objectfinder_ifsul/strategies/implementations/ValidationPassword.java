package br.ifsul.objectfinder_ifsul.strategies.implementations;

import br.ifsul.objectfinder_ifsul.strategies.interfaces.IValidation;

public class ValidationPassword implements IValidation {
    @Override
    public boolean validate(String data) {
        return data.length() <= 12;
    }
}
