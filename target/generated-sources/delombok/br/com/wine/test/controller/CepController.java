package br.com.wine.test.controller;

import br.com.wine.test.constants.Constants;
import br.com.wine.test.model.Cep;
import br.com.wine.test.model.Cidade;
import br.com.wine.test.repository.CepRepository;
import br.com.wine.test.repository.CidadeRepository;
import br.com.wine.test.utils.IgnoreField;
import br.com.wine.test.utils.Mask;
import br.com.wine.test.viacep.vo.ViaCEPVO;
import com.google.gson.GsonBuilder;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author jonny
 */
@RestController
public class CepController {

    @Autowired
    private CepRepository cepRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    /**
     * Retorna os dados de endereço para o CEP informado.
     *
     * @param cepParam
     * @return
     */
    @GetMapping("/cep/{cep}")
    @ResponseBody
    public ResponseEntity<String> getCEP(@PathVariable(name = "cep", required = true) String cepParam) {
        cepParam = Mask.unmaskCep(cepParam);
        Optional<Cep> optionalCep = cepRepository.findById(cepParam);
        Cep cep;
        if (optionalCep.isPresent()) {
            cep = optionalCep.get();
        } else {
            ViaCEPVO viaCepVO = null;
            try {
                RestTemplate restTemplate = new RestTemplate();
                viaCepVO = restTemplate.getForObject(Constants.URL_CONSULTA_CEP.replace(Constants.CEP_PARAM, cepParam), ViaCEPVO.class);
            } catch (Exception ex) {
                return ResponseEntity.badRequest().body("Erro ao consultar o CEP no ViaCEP");
            }
            if (Objects.nonNull(viaCepVO) && !viaCepVO.isErro()) {
                cep = Cep.builder().
                        cep(cepParam).
                        logradouro(viaCepVO.getLogradouro()).
                        complemento(viaCepVO.getComplemento()).
                        bairro(viaCepVO.getBairro()).
                        build();

                Cidade cidade;
                try {
                    cidade = cidadeRepository.findById(viaCepVO.getIbge()).get();
                } catch (NoSuchElementException nsee) {
                    cidade = Cidade.builder().ibge(viaCepVO.getIbge()).localidade(viaCepVO.getLocalidade()).uf(viaCepVO.getUf()).build();
                    cidade = cidadeRepository.save(cidade);
                }
                cep.setCidade(cidade);
                cepRepository.save(cep);

            } else {
                return ResponseEntity.badRequest().body("CEP não encontrado");
            }
        }
        cep.setCep(Mask.maskCep(cep.getCep()));

        return ResponseEntity.ok().body(new GsonBuilder().
                addSerializationExclusionStrategy(IgnoreField.getStrategy("ceps", Cidade.class)).
                serializeNulls().create().toJson(cep));
    }

    /**
     * Consulta e retorna todos os CEPS para o IBGE e UF informados. (parâmetro
     * UF desnecessário).
     *
     * @param ibge
     * @param uf
     * @return
     */
    @GetMapping("/ceps")
    @ResponseBody
    public ResponseEntity<String> getCeps(@RequestParam(name = "ibge", required = true) String ibge, @RequestParam(name = "uf", required = true) String uf) {
        try {
            Cidade cidade = cidadeRepository.findById(ibge).get();
            return ResponseEntity.ok().body(new GsonBuilder().
                    addSerializationExclusionStrategy(IgnoreField.getStrategy("cidade", Cep.class)).
                    serializeNulls().create().toJson(cidade));
        } catch (NoSuchElementException nsee) {
            return ResponseEntity.ok().body("Nenhuma localidade encontrada para os parâmetros informados");
        }
    }
}
