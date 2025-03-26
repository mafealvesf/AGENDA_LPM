import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {
            System.out.println("\nGerenciador de Compromissos");
            System.out.println("1. Adicionar compromisso");
            System.out.println("2. Listar compromissos");
            System.out.println("3. Buscar compromissos por data");
            System.out.println("4. Gerar relatório de compromissos");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            if (opcao == 1) {
                System.out.print("Descrição: ");
                String descricao = scanner.nextLine();
                
                System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
                String dataHora = scanner.nextLine();
                LocalDateTime momento = LocalDateTime.parse(dataHora, formatter);
                
                System.out.print("Tipo (PROFISSIONAL, PESSOAL, LAZER): ");
                TipoCompromisso tipo = TipoCompromisso.valueOf(scanner.nextLine().toUpperCase());
                
                try {
                    Compromisso compromisso = new Compromisso(descricao, momento, tipo);
                    agenda.adicionarCompromisso(compromisso);
                    System.out.println("Compromisso adicionado com sucesso!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if (opcao == 2) {
                List<Compromisso> compromissos = agenda.listarTodos();
                if (compromissos.isEmpty()) {
                    System.out.println("Nenhum compromisso cadastrado.");
                } else {
                    System.out.println("Lista de compromissos:");
                    for (Compromisso c : compromissos) {
                        System.out.println(c);
                    }
                }
            } else if (opcao == 3) {
                System.out.print("Informe a data (dd/MM/yyyy): ");
                String data = scanner.nextLine();
                LocalDate dataBusca = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                List<Compromisso> resultado = agenda.buscarPorData(dataBusca);
                if (resultado.isEmpty()) {
                    System.out.println("Nenhum compromisso encontrado para essa data.");
                } else {
                    resultado.forEach(System.out::println);
                }
            } else if (opcao == 4) {
                agenda.relatorioCompromissos(); 
            }
            else if (opcao == 5) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}
