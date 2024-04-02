package onboardingMarcos.tinelli.dto;

public class SelicDTO {

  private String nome;
  private String valor;

  public SelicDTO() {
  }

  public SelicDTO(String nome, String valor) {
    this.nome = nome;
    this.valor = valor;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }
}
