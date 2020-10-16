package br.com.wine.test.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@ToString(exclude = "ceps")
@EqualsAndHashCode(of = "ibge")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TCidade")
public class Cidade implements Serializable {

    @Id
    @Column(nullable = false, length = 20)
    private String ibge;
    @Column(nullable = false, length = 2)
    private String uf;
    @Column(nullable = false, length = 100)
    private String localidade;
    @OneToMany(mappedBy = "cidade")
    private List<Cep> ceps;
}
