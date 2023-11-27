package br.ifsul.objectfinder_ifsul.design_patterns.strategy.implementations;

import java.util.regex.Pattern;
import br.ifsul.objectfinder_ifsul.design_patterns.strategy.interfaces.IValidation;

public class ValidationEmail implements IValidation  {
    @Override
    public boolean validate(String data) {
        Pattern patternEmail = Pattern.compile("[a-z]+[a-z_0-9-]+@[a-z]+(\\.[a-z]+)+");
        return patternEmail.matcher(data).matches();
    }
}
