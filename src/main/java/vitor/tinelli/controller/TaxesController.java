package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Taxes;
import vitor.tinelli.requests.TaxesPostRequestBody;
import vitor.tinelli.requests.TaxesPutRequestBody;
import vitor.tinelli.service.TaxesService;

@RestController
@RequestMapping("/taxes")
@RequiredArgsConstructor
public class TaxesController {

  private final TaxesService taxesService;

  @GetMapping
  public ResponseEntity<List<Taxes>> listAll() {
    return ResponseEntity.ok(taxesService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Taxes> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(taxesService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Taxes> save (@RequestBody @Valid TaxesPostRequestBody taxesPostRequestBody) {
    return ResponseEntity.ok(taxesService.save(taxesPostRequestBody));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    taxesService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> replace (@RequestBody @Valid TaxesPutRequestBody taxesPutRequestBody) {
    taxesService.replace(taxesPutRequestBody);
    return ResponseEntity.noContent().build();
  }
}
