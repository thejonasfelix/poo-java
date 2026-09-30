package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.factory.OperacaoFactory;

public class BuscarCategoriaComMaiorGastoProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
              // maior soma de saídas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Mercado", -100.0),
                    OperacaoFactory.getInstance("Lazer", "Cinema", -60.0),
                    OperacaoFactory.getInstance("Alimentacao", "Padaria", -30.0),
                    OperacaoFactory.getInstance("Gasto fixo", "Agua", -120.0)), "Alimentacao"),

              // soma do total da categoria e não o maior valor individual
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Gasto fixo", "Energia", -200.0),
                    OperacaoFactory.getInstance("Lazer", "Cinema", -80.0),
                    OperacaoFactory.getInstance("Lazer", "PSN", -90.0),
                    OperacaoFactory.getInstance("Lazer", "Show", -70.0)), "Lazer"),

              // maior soma não é a categoria com mais operações
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Padaria", -10.0),
                    OperacaoFactory.getInstance("Alimentacao", "Mercado", -10.0),
                    OperacaoFactory.getInstance("Alimentacao", "Feira", -10.0),
                    OperacaoFactory.getInstance("Gasto fixo", "Aluguel", -500.0)), "Gasto fixo"),

              // agrupa categorias ignorando maiúsculas e minúsculas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Lazer", "Cinema", -60.0),
                    OperacaoFactory.getInstance("Gasto fixo", "Agua", -100.0),
                    OperacaoFactory.getInstance("LAZER", "PSN", -50.0)), "Lazer"),

              // empate retorna a categoria que aparece primeiro
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Lazer", "Cinema", -100.0),
                    OperacaoFactory.getInstance("Gasto fixo", "Agua", -100.0)), "Lazer"),

              // ignora as entradas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Salario", "Empresa", 5000.0),
                    OperacaoFactory.getInstance("Lazer", "Cinema", -20.0)), "Lazer"),

              // somente entradas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Salario", "Empresa", 5000.0)), null),

              // sem operações
              Arguments.of(List.of(), null)
        );
    }
}
