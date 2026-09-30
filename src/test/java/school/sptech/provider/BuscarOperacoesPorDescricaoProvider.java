package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class BuscarOperacoesPorDescricaoProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Operacao o1 = OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", -17.50);
        Operacao o2 = OperacaoFactory.getInstance("Gasto fixo", "agua", -179.0);
        Operacao o3 = OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", -50.0);
        Operacao o4 = OperacaoFactory.getInstance("Gasto fixo", "energia", -234.0);
        Operacao o5 = OperacaoFactory.getInstance("Lazer", "Cinema", -50.0);
        Operacao o6 = OperacaoFactory.getInstance("Lazer", "Cinema 2", -50.0);
        List<Operacao> operacoes = List.of(o1, o2, o3, o4, o5, o6);

        return Stream.of(
              // descrição exata (apenas uma operação, pois "Cinema 2" não é igual)
              Arguments.of(operacoes, "Comercio Dona Maria", List.of(o1)),

              // descrição parcial
              Arguments.of(operacoes, "Cine", List.of(o5, o6)),
              Arguments.of(operacoes, "Comercio", List.of(o1, o3)),

              // ignora maiúsculas e minúsculas
              Arguments.of(operacoes, "CInEMA", List.of(o5, o6)),
              Arguments.of(operacoes, "AGUA", List.of(o2)),

              // nenhuma operação encontrada
              Arguments.of(operacoes, "Academia", List.of()),

              // descrição nula
              Arguments.of(operacoes, null, List.of()),

              // conta sem operações
              Arguments.of(List.of(), "Cinema", List.of())
        );
    }
}
