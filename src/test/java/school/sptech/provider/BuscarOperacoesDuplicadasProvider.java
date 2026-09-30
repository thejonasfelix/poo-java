package school.sptech.provider;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Operacao;
import school.sptech.factory.OperacaoFactory;

public class BuscarOperacoesDuplicadasProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Operacao netflix1 = OperacaoFactory.getInstance("Lazer", "Netflix", -40.0);
        Operacao agua1 = OperacaoFactory.getInstance("Gasto fixo", "Agua", -100.0);
        Operacao netflix2 = OperacaoFactory.getInstance("Lazer", "Netflix", -40.0);
        Operacao netflixMaiusculo = OperacaoFactory.getInstance("LAZER", "netflix", -40.0);
        Operacao netflixOutroValor = OperacaoFactory.getInstance("Lazer", "Netflix", -45.0);
        Operacao netflixOutraCategoria = OperacaoFactory.getInstance("Assinatura", "Netflix",
              -40.0);
        Operacao spotify = OperacaoFactory.getInstance("Lazer", "Spotify", -40.0);
        Operacao salario = OperacaoFactory.getInstance("Salario", "Empresa", 3000.0);
        Operacao agua2 = OperacaoFactory.getInstance("Gasto fixo", "Agua", -100.0);
        Operacao netflix3 = OperacaoFactory.getInstance("Lazer", "Netflix", -40.0);
        Operacao mercado = OperacaoFactory.getInstance("Alimentacao", "Mercado", -80.0);

        return Stream.of(
              // par duplicado, na ordem original
              Arguments.of(List.of(netflix1, agua1, netflix2), List.of(netflix1, netflix2)),

              // ignora maiúsculas e minúsculas em categoria e descrição
              Arguments.of(List.of(netflix1, netflixMaiusculo),
                    List.of(netflix1, netflixMaiusculo)),

              // valor diferente
              Arguments.of(List.of(netflix1, netflixOutroValor), List.of()),

              // categoria ou descrição diferentes
              Arguments.of(List.of(netflix1, netflixOutraCategoria, spotify), List.of()),

              // mais de duas cópias: cada operação aparece uma única vez
              Arguments.of(List.of(netflix1, netflix2, netflix3),
                    List.of(netflix1, netflix2, netflix3)),

              // vários grupos de duplicadas mantendo a ordem original
              Arguments.of(List.of(netflix1, agua1, salario, agua2, netflix2, mercado),
                    List.of(netflix1, agua1, agua2, netflix2)),

              // uma operação não é duplicada dela mesma
              Arguments.of(List.of(netflix1), List.of()),

              // conta sem operações
              Arguments.of(List.of(), List.of())
        );
    }
}
