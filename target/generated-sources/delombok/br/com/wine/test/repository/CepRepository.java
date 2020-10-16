package br.com.wine.test.repository;

import br.com.wine.test.model.Cep;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonny
 */
@Repository
public interface CepRepository extends CrudRepository<Cep, String> {

}
