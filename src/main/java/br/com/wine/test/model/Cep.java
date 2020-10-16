package br.com.wine.test.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author jonny
 */
@Data
@ToString(exclude = "cidade")
@EqualsAndHashCode(of = "cep")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TCep")
public class Cep implements Serializable {

    @Id
    @Column(nullable = false, length = 10)
    private String cep;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String logradouro;
    @Column(columnDefinition = "varchar(255)")
    private String complemento;
    @Column(columnDefinition = "varchar(50)")
    private String bairro;
    @ManyToOne
    @JoinColumn(name = "ibge")
    private Cidade cidade;

}
