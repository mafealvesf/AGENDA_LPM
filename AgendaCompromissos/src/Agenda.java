import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class Agenda {
    private static final int MAX_COMPROMISSOS = 300;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<Compromisso> compromissos;
    private Map<LocalDate, Integer> quantCompromissos;

    public Agenda() {
        this.compromissos = new ArrayList<>();
        this.quantCompromissos = new HashMap<>();

    }

    public void adicionarCompromisso(Compromisso compromisso) {
        if (compromissos.size() >= MAX_COMPROMISSOS) {
            System.out.println("❌ Limite máximo de compromissos atingido!");
            return;
        }
        compromissos.add(compromisso);

        LocalDate data = compromisso.getMomento().toLocalDate();
        quantCompromissos.put(data, quantCompromissos.getOrDefault(data, 0) + 1);
    }

    public List<Compromisso> buscarPorData(LocalDate data) {
        return compromissos.stream()
                .filter(c -> c.getMomento().toLocalDate().equals(data))
                .collect(Collectors.toList());
    }

    public List<Compromisso> listarTodos() {
        return new ArrayList<>(compromissos);
    }

    public void relatorioCompromissos() {
        if (compromissos.isEmpty()) {
            System.out.println("📭 Nenhum compromisso registrado.");
            return;
        }

        StringBuilder relatorio = new StringBuilder("\n--- RELATÓRIO DE COMPROMISSOS ---\n");

        for (Compromisso c : compromissos) {
            relatorio.append("ID: ").append(c.getId()).append("\n")
                     .append("Descrição: ").append(c.getDescricao()).append("\n")
                     .append("Data e Hora: ").append(c.getMomento().format(FORMATTER)).append("\n")
                     .append("Tipo: ").append(c.getTipo()).append("\n")
                     .append("-------------------------------------\n");
        }

        relatorio.append("\n--- TOTAL DE COMPROMISSOS POR DIA ---\n");
        for (Map.Entry<LocalDate, Integer> entry : quantCompromissos.entrySet()) {
            relatorio.append("Data: ").append(entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                     .append(" - ").append(entry.getValue()).append(" compromisso(s)\n");
        }
    
    }

}
