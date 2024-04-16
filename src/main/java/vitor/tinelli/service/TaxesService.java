package vitor.tinelli.service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vitor.tinelli.domain.Taxes;
import vitor.tinelli.exceptions.BadRequestException;
import vitor.tinelli.repository.TaxesRepository;
import vitor.tinelli.requests.TaxesPostRequestBody;
import vitor.tinelli.requests.TaxesPutRequestBody;

@Service
@RequiredArgsConstructor
public class TaxesService {

  private final TaxesRepository taxesRepository;

  public List<Taxes> listAll() {
    return taxesRepository.findAll();
  }

  public Taxes findByIdOrThrowBadRequestException(UUID id) {
    return taxesRepository.findById(id).orElseThrow(
        () -> new BadRequestException("Tax not found, please verify the provided ID"));
  }

  @Transactional
  public Taxes save(TaxesPostRequestBody taxesPostRequestBody) {
    return taxesRepository.save(Taxes.builder()
        .name(taxesPostRequestBody.getName())
        .aliquot(taxesPostRequestBody.getAliquot())
        .build());
  }

  @Transactional
  public void delete(UUID id) {
    findByIdOrThrowBadRequestException(id);
    taxesRepository.deleteById(id);
  }

  @Transactional
  public void replace(TaxesPutRequestBody taxesPutRequestBody) {
    Taxes savedTaxes = findByIdOrThrowBadRequestException(taxesPutRequestBody.getId());
    taxesRepository.save(Taxes.builder()
        .id((savedTaxes.getId()))
        .name(taxesPutRequestBody.getName())
        .aliquot(taxesPutRequestBody.getAliquot())
        .build());
  }
}

