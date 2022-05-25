package org.techdive.exception;

public class RegistroNaoEncontradoException extends Throwable {

    private String tipoRegistro;
    private String identificador;

    public RegistroNaoEncontradoException(String tipoRegistro, String identificador) {
        this.tipoRegistro = tipoRegistro;
        this.identificador = identificador;
    }

    public String getMessage() {
        return String.format("%s: Registro não encontrado com identificador: %s", tipoRegistro, identificador);
    }

}
