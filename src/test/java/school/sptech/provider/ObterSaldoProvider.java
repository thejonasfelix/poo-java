package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class ObterSaldoProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
              // saldo positivo
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", 17.50),
                    OperacaoFactory.getInstance("Gasto fixo", "agua", -179.0),
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", -2.0),
                    OperacaoFactory.getInstance("Gasto fixo", "energia", -234.0),
                    OperacaoFactory.getInstance("Lazer", "ciNema", 500.0),
                    OperacaoFactory.getInstance("Lazer", "cinemA 2", -50.0)), 52.5),

              // saldo negativo
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", 17.50),
                    OperacaoFactory.getInstance("Gasto fixo", "agua", -179.0),
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", -2.0),
                    OperacaoFactory.getInstance("Gasto fixo", "energia", -234.0),
                    OperacaoFactory.getInstance("Lazer", "ciNema", 50.0),
                    OperacaoFactory.getInstance("Lazer", "cinemA 2", -50.0)), -397.5),

              // somente entradas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", 17.50),
                    OperacaoFactory.getInstance("Gasto fixo", "agua", 179.0),
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", 2.0),
                    OperacaoFactory.getInstance("Gasto fixo", "energia", 234.0),
                    OperacaoFactory.getInstance("Lazer", "ciNema", 50.0),
                    OperacaoFactory.getInstance("Lazer", "cinemA 2", 50.0)), 532.5),

              // somente saídas
              Arguments.of(List.of(
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", -17.50),
                    OperacaoFactory.getInstance("Gasto fixo", "agua", -179.0),
                    OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", -2.0),
                    OperacaoFactory.getInstance("Gasto fixo", "energia", -234.0),
                    OperacaoFactory.getInstance("Lazer", "ciNema", -50.0),
                    OperacaoFactory.getInstance("Lazer", "cinemA 2", -50.0)), -532.5),

              // conta sem operações
              Arguments.of(List.of(), 0.0)
        );
    }
}
