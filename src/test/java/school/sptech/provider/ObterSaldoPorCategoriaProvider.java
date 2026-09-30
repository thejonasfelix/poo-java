package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class ObterSaldoPorCategoriaProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        List<Operacao> operacoes = List.of(
              OperacaoFactory.getInstance("Lazer", "Cinema", -50.0),
              OperacaoFactory.getInstance("Gasto fixo", "Agua", -100.0),
              OperacaoFactory.getInstance("Lazer", "Salario extra", 200.0),
              OperacaoFactory.getInstance("LAZER", "PSN", -30.5));

        return Stream.of(
              // soma entradas e saídas da categoria
              Arguments.of(operacoes, "Lazer", 119.5),

              // ignora maiúsculas e minúsculas
              Arguments.of(operacoes, "lAzEr", 119.5),

              // categoria só com saídas
              Arguments.of(operacoes, "Gasto fixo", -100.0),

              // categoria sem operações
              Arguments.of(operacoes, "Viagem", 0.0),

              // categoria nula
              Arguments.of(operacoes, null, 0.0),

              // categoria em branco
              Arguments.of(operacoes, "   ", 0.0),

              // conta sem operações
              Arguments.of(List.of(), "Lazer", 0.0)
        );
    }
}
