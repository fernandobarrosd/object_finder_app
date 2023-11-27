package br.ifsul.objectfinder_ifsul.dto;


public class LoginDTO {
    private String email;
    private String password;

    private LoginDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class LoginBuilder {
        private final LoginDTO loginDTO;

        public LoginBuilder() {
            loginDTO = new LoginDTO();
        }

        public LoginBuilder email(String email) {
            loginDTO.setEmail(email);
            return this;
        }

        public LoginBuilder password(String password) {
            loginDTO.setPassword(password);
            return this;
        }

        public LoginDTO getLoginDTO() {
            return loginDTO;
        }
    }
}
