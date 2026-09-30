package school.sptech.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class AdicionarOperacaoInvalidaProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
              // categoria inválida
              Arguments.of("  ", "Mercado x", -100.0),

              // descrição inválida
              Arguments.of("Gasto fixo", "   ", 100.0),

              // valor null
              Arguments.of("Gasto fixo", "Comércio do seu Jóse", null),

              // tudo inválido
              Arguments.of(" ", "", null),

              // tudo null
              Arguments.of(null, null, null),

              // valor zero
              Arguments.of("Alimentacao", "Comercio Dona Maria", 0.0)
        );
    }
}
