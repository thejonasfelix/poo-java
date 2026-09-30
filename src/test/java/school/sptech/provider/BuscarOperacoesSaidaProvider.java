package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class BuscarOperacoesSaidaProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Operacao o1 = OperacaoFactory.getInstance("Alimentacao", "Comercio Dona Maria", -17.50);
        Operacao o2 = OperacaoFactory.getInstance("Gasto fixo", "Agua", -179.0);
        Operacao o3 = OperacaoFactory.getInstance("Alimentacao", "Comercio Seu João", 50.0);
        Operacao o4 = OperacaoFactory.getInstance("Gasto fixo", "Energia", -234.0);
        Operacao o5 = OperacaoFactory.getInstance("Lazer", "Cinema", 50.0);

        return Stream.of(
              // entradas e saídas misturadas
              Arguments.of(List.of(o1, o2, o3, o4, o5), List.of(o1, o2, o4)),

              // somente saídas
              Arguments.of(List.of(o1, o2, o4), List.of(o1, o2, o4)),

              // somente entradas
              Arguments.of(List.of(o3, o5), List.of()),

              // conta sem operações
              Arguments.of(List.of(), List.of())
        );
    }
}
