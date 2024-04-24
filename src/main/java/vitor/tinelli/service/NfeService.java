package vitor.tinelli.service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import vitor.tinelli.domain.Nfe;
import vitor.tinelli.exceptions.BadRequestException;
import vitor.tinelli.repository.NfeRepository;
import vitor.tinelli.requests.NfePostRequestBody;
import vitor.tinelli.requests.NfePutRequestBody;

@Service
public class NfeService {

  private final NfeRepository nfeRepository;

  public NfeService(NfeRepository nfeRepository) {
    this.nfeRepository = nfeRepository;
  }

  public List<Nfe> listAll() {
    return nfeRepository.findAll();
  }

  public Nfe findByIdOrThrowBadRequestException(UUID id) {
    return nfeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Nfe not found, please verify the provided ID"));
  }

  @Transactional
  public Nfe save(NfePostRequestBody nfePostRequestBody) {
    return nfeRepository.save(Nfe.builder()
        .number(nfePostRequestBody.getNumber())
        .date(nfePostRequestBody.getDate())
        .value(nfePostRequestBody.getValue())
        .build());
  }

  @Transactional
  public void delete(UUID id) {
    findByIdOrThrowBadRequestException(id);
    nfeRepository.deleteById(id);
  }


  @Transactional
  public void replace(NfePutRequestBody nfePutRequestBody) {
    Nfe savedNfe = findByIdOrThrowBadRequestException(nfePutRequestBody.getId());
    nfeRepository.save(Nfe.builder()
        .id(savedNfe.getId())
        .number(nfePutRequestBody.getNumber())
        .date(nfePutRequestBody.getDate())
        .value(nfePutRequestBody.getValue())
        .build());
  }

  public List<Nfe> findByDateGap(LocalDate startDate, LocalDate endDate) {
    return nfeRepository.findByDateBetween(startDate, endDate);
  }
}
