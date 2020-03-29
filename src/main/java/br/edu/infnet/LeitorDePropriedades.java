package br.edu.infnet;

import java.io.IOException;
import java.util.Properties;

public class LeitorDePropriedades {
    private Properties props;

    public LeitorDePropriedades(String nomeDoArquivo) {
        props = new Properties();
        var in = this.getClass().getResourceAsStream(nomeDoArquivo);

        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final String getValor(String chave) {
        return props.getProperty(chave);
    }
}
