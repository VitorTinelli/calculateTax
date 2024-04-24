package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Nfe;
import vitor.tinelli.requests.DateGapRequestBody;
import vitor.tinelli.requests.NfePostRequestBody;
import vitor.tinelli.requests.NfePutRequestBody;
import vitor.tinelli.service.NfeService;

@RestController
@RequestMapping("/nfe")
@RequiredArgsConstructor
public class NfeController {

  private final NfeService nfeService;

  @GetMapping
  public ResponseEntity<List<Nfe>> listAll() {
    return ResponseEntity.ok(nfeService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Nfe> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(nfeService.findByIdOrThrowBadRequestException(id));
  }

  @GetMapping("/date")
  public ResponseEntity<List<Nfe>> findByDateGap(@RequestBody @Valid DateGapRequestBody dateGap) {
    return ResponseEntity.ok(
        nfeService.findByDateGap(dateGap.getStartDate(), dateGap.getEndDate()));
  }

  @PostMapping
  public ResponseEntity<Nfe> save(@RequestBody @Valid NfePostRequestBody nfePostRequestBody) {
    return ResponseEntity.ok(nfeService.save(nfePostRequestBody));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    nfeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody NfePutRequestBody nfePutRequestBody) {
    nfeService.replace(nfePutRequestBody);
    return ResponseEntity.noContent().build();
  }

}
