import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

enum TipoCompromisso {
    PROFISSIONAL, PESSOAL, LAZER;
}

public class Compromisso implements Comparable<Compromisso> {
    private static int ultimoID = 0;

    private final int id;
    private String descricao;
    private LocalDateTime momento;
    private TipoCompromisso tipo;

    public Compromisso(String descricao, LocalDateTime momento, TipoCompromisso tipo) {
        this.id = ultimoID++;
        if (momento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O compromisso não pode ser agendado, data passada.");
        }
        this.descricao = descricao;
        this.momento = momento;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    public TipoCompromisso getTipo() {
        return tipo;
    }

    public void reagendar(LocalDateTime novoMomento) {
        if (novoMomento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O novo momento deve ser no futuro.");
        }
        this.momento = novoMomento;
    }

    public boolean estaNaFaixa(LocalDateTime inicio, LocalDateTime fim) {
        return (momento.isEqual(inicio) || momento.isAfter(inicio)) && momento.isBefore(fim);
    }

    @Override
    public int compareTo(Compromisso outro) {
        return this.momento.compareTo(outro.momento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compromisso)) return false;
        Compromisso that = (Compromisso) o;
        return Objects.equals(descricao, that.descricao) &&
                Objects.equals(momento, that.momento) &&
                tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, momento, tipo);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s [%s] - %s", tipo, descricao, momento.format(formatter));
    }
}
