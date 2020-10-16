package br.com.wine.test.viacep.vo;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author jonny
 */
@XmlRootElement
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ViaCEPVO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
    private boolean erro;
}
