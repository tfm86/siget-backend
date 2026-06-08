package com.siget.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email){
        super("O e-mail"+ email +"Já está cadastrado");
    }
}
