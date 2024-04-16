package vitor.tinelli.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.NfeTax;
import vitor.tinelli.service.NfeTaxService;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class NfeTaxController {

  private final NfeTaxService nfeTaxService;

  @GetMapping("/list-all")
  public ResponseEntity<List<NfeTax>> listAll() {
    return ResponseEntity.ok(nfeTaxService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<NfeTax>> findByNfe(@PathVariable UUID id) {
    return ResponseEntity.ok(nfeTaxService.listByNfeID(id));
  }

  @PostMapping("/post-all")
  public ResponseEntity<List<NfeTax>> postAllNfeTax() {
    return ResponseEntity.ok(nfeTaxService.postEveryNfeWithoutTax());
  }


}
