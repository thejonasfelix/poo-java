package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class BuscarOperacoesPorValorProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Operacao o1 = OperacaoFactory.getInstance("Lazer", "PSN", 17.50);
        Operacao o2 = OperacaoFactory.getInstance("Gasto fixo", "Agua", 179.0);
        Operacao o3 = OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", 17.50);
        Operacao o4 = OperacaoFactory.getInstance("Gasto fixo", "Energia", 234.0);
        Operacao o5 = OperacaoFactory.getInstance("Lazer", "Cinema", 50.0);
        List<Operacao> operacoes = List.of(o1, o2, o3, o4, o5);

        return Stream.of(
              // apenas uma operação com o valor
              Arguments.of(operacoes, 50.0, List.of(o5)),

              // mais de uma operação com o valor
              Arguments.of(operacoes, 17.50, List.of(o1, o3)),

              // nenhuma operação com o valor
              Arguments.of(operacoes, 1000.0, List.of()),

              // conta sem operações
              Arguments.of(List.of(), 17.50, List.of())
        );
    }
}
