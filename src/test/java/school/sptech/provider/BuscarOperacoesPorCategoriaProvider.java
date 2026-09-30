package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class BuscarOperacoesPorCategoriaProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Operacao o1 = OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", 17.50);
        Operacao o2 = OperacaoFactory.getInstance("Gasto fixo", "Agua", 179.0);
        Operacao o3 = OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", 50.0);
        Operacao o4 = OperacaoFactory.getInstance("Gasto fixo", "Energia", 234.0);
        List<Operacao> operacoes = List.of(o1, o2, o3, o4);

        return Stream.of(
              // categoria válida
              Arguments.of(operacoes, "Alimentacao", List.of(o1, o3)),

              // ignora maiúsculas e minúsculas
              Arguments.of(operacoes, "ALIMENTACAO", List.of(o1, o3)),
              Arguments.of(operacoes, "gasto FIXO", List.of(o2, o4)),

              // categoria sem operações
              Arguments.of(operacoes, "Lazer", List.of()),

              // conta sem operações
              Arguments.of(List.of(), "Lazer", List.of())
        );
    }
}
