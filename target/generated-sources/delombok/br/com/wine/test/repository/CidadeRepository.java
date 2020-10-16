package br.com.wine.test.repository;

import br.com.wine.test.model.Cidade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonny
 */
@Repository
public interface CidadeRepository extends CrudRepository<Cidade, String> {

}
