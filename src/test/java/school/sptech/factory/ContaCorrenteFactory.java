package school.sptech.factory;

import java.util.ArrayList;
import school.sptech.ContaCorrente;
import school.sptech.util.ObjectFieldBuilder;

public class ContaCorrenteFactory {

    public static ContaCorrente getInstance(
          Object nomeConta,
          Object numeroConta,
          Object agenciaConta
    ) throws ReflectiveOperationException {
        return new ObjectFieldBuilder<>(ContaCorrente.class)
              .with("titular", nomeConta)
              .with("numero", numeroConta)
              .with("agencia", agenciaConta)
              .with("operacoes", new ArrayList<>())
              .build();
    }

    public static ContaCorrente getInstance(
          Object nomeConta,
          Object numeroConta,
          Object agenciaConta,
          Object operacoes
    ) throws ReflectiveOperationException {
        return new ObjectFieldBuilder<>(ContaCorrente.class)
              .with("titular", nomeConta)
              .with("numero", numeroConta)
              .with("agencia", agenciaConta)
              .with("operacoes", operacoes)
              .build();
    }
}
