package school.sptech.factory;

import school.sptech.Operacao;
import school.sptech.util.ObjectFieldBuilder;

public class OperacaoFactory {

    public static Operacao getInstance(Object categoria, Object descricao, Object valor)
          throws ReflectiveOperationException {

        return new ObjectFieldBuilder<>(Operacao.class)
              .with("categoria", categoria)
              .with("descricao", descricao)
              .with("valor", valor)
              .build();
    }
}
