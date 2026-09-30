package school.sptech;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import school.sptech.util.EncapsulationUtil;

@DisplayName("Operacao")
public class OperacaoTest {

    @Nested
    @DisplayName("Operacao 01. Atributos")
    class AtributosTests {

        @Test
        @DisplayName("Validar Atributos")
        void cenario1() {
            Class<Operacao> clazz = Operacao.class;

            Assertions.assertAll(
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("categoria")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("descricao")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("valor"))
            );
        }
    }

    @Nested
    @DisplayName("Operacao 02. Encapsulamento")
    class EncapsulamentoTests {

        @Test
        @DisplayName("Atributos Privados")
        void cenario1() {
            Class<Operacao> clazz = Operacao.class;
            Field[] campos = clazz.getDeclaredFields();

            Stream<Executable> validacoes = Arrays.stream(campos)
                  .map((campo) -> () -> Assertions.assertTrue(
                        Modifier.isPrivate(campo.getModifiers())));

            Assertions.assertAll(validacoes);
        }

        @Test
        @DisplayName("Atributos devem possuir getters e setters")
        void cenario2() {
            Class<Operacao> clazz = Operacao.class;
            EncapsulationUtil.validarGettersSetters(clazz);
        }
    }


    @Nested
    @DisplayName("Operacao 03. Deve possuir um construtor cheio")
    class OperacaoConstrutorTests {

        @Test
        @DisplayName("Cenário Único")
        void test1() throws NoSuchMethodException {

            Class<Operacao> clazz = Operacao.class;
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();

            Assertions.assertTrue(constructors.length > 0);

            boolean hasFullConstructor = Arrays.stream(constructors)
                  .anyMatch(constructor -> {

                      Parameter[] parameters = constructor.getParameters();

                      long qtdString = Arrays.stream(parameters)
                            .filter(parameter -> parameter.getType().equals(String.class))
                            .count();

                      long qtdDouble = Arrays.stream(parameters)
                            .filter(parameter -> parameter.getType().equals(Double.class))
                            .count();

                      return qtdString >= 2 && qtdDouble >= 1;
                  });

            Assertions.assertTrue(hasFullConstructor);
        }
    }
}
