package school.sptech;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import school.sptech.factory.ContaCorrenteFactory;
import school.sptech.factory.OperacaoFactory;
import school.sptech.provider.AdicionarOperacaoInvalidaProvider;
import school.sptech.provider.BuscarMenorValorProvider;
import school.sptech.provider.BuscarOperacoesDuplicadasProvider;
import school.sptech.provider.BuscarOperacoesPorCategoriaProvider;
import school.sptech.provider.BuscarOperacoesPorDescricaoProvider;
import school.sptech.provider.BuscarOperacoesPorValorProvider;
import school.sptech.provider.BuscarOperacoesSaidaProvider;
import school.sptech.provider.ObterSaldoProvider;
import school.sptech.provider.BuscarCategoriaComMaiorGastoProvider;
import school.sptech.provider.ObterSaldoPorCategoriaProvider;
import school.sptech.util.EncapsulationUtil;

@DisplayName("ContaCorrente")
public class ContaCorrenteTest {

    @Nested
    @DisplayName("ContaCorrente 01. Atributos")
    class AtributosTests {

        @Test
        @DisplayName("Validar Atributos")
        void cenario1() {
            Class<ContaCorrente> clazz = ContaCorrente.class;

            Assertions.assertAll(
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("titular")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("agencia")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("numero")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredField("operacoes"))
            );
        }
    }

    @Nested
    @DisplayName("ContaCorrente 02. Encapsulamento")
    class EncapsulamentoTests {

        @Test
        @DisplayName("Atributos Privados")
        void cenario1() {
            Class<ContaCorrente> clazz = ContaCorrente.class;
            Field[] campos = clazz.getDeclaredFields();

            Stream<Executable> validacoes = Arrays.stream(campos)
                  .map((campo) -> () -> Assertions.assertTrue(
                        Modifier.isPrivate(campo.getModifiers())));

            Assertions.assertAll(validacoes);
        }

        @Test
        @DisplayName("Atributos devem possuir getters e setters")
        void cenario2() {
            Class<ContaCorrente> clazz = ContaCorrente.class;
            EncapsulationUtil.validarGettersSettersExcetoSetters(clazz, "operacoes");
        }

        @Test
        @DisplayName("Atributo operacoes não deve conter setter")
        void atributoOperacoesNaoDeveConterSettersTests()
              throws NoSuchFieldException, NoSuchMethodException {

            Class<ContaCorrente> clazz = ContaCorrente.class;
            Field field = clazz.getDeclaredField("operacoes");

            String setName = String.format("set%s", StringUtils.capitalize(field.getName()));

            Assertions.assertThrows(NoSuchMethodException.class, () -> {
                clazz.getDeclaredMethod(setName, field.getType());
            });
        }
    }

    @Nested
    @DisplayName("ContaCorrente 03. A lista de operações deve ser inicializada no momento da criação do objeto")
    class AtributoOperacoesDeveSerInicializadoNaCriacaoDoObjetoTests {

        @Test
        @DisplayName("Único Cenário")
        void test1()
              throws NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {

            Class<ContaCorrente> clazz = ContaCorrente.class;

            Field field = clazz.getDeclaredField("operacoes");

            Constructor<?> constructor = clazz.getConstructors()[0];

            Object[] args = new Object[constructor.getParameterCount()];

            Object instance = constructor.newInstance(args);

            field.trySetAccessible();
            Object value = field.get(instance);

            Assertions.assertNotNull(value);
        }
    }

    @Nested
    @DisplayName("ContaCorrente 04. Métodos")
    class MetodosTests {

        @Test
        @DisplayName("Validar Métodos")
        void validarMetodos() {
            Class<ContaCorrente> clazz = ContaCorrente.class;

            Assertions.assertAll(
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("adicionarOperacao", String.class,
                              String.class, Double.class)),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarOperacoesPorCategoria", String.class)),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarOperacoesPorValor", Double.class)),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarOperacoesSaida")),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarOperacoesPorDescricao", String.class)),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarMenorValor")),
                  () -> Assertions.assertDoesNotThrow(() -> clazz.getDeclaredMethod("obterSaldo")),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("obterSaldoPorCategoria", String.class)),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarCategoriaComMaiorGasto")),
                  () -> Assertions.assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarOperacoesDuplicadas"))
            );
        }
    }

    @Nested
    @DisplayName("ContaCorrente 05. Adicionar operação")
    class AdicionarOperacaoTests {

        @Test
        @DisplayName("Adicionar operação com categoria, descrição e valor válidos")
        void test1() throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456");
            Class<?> clazz = ContaCorrente.class;

            Field campoOperacoes = clazz.getDeclaredField("operacoes");
            campoOperacoes.trySetAccessible();

            Method adicionarOperacao = clazz.getDeclaredMethod("adicionarOperacao", String.class,
                  String.class, Double.class);

            adicionarOperacao.invoke(contaCorrente, "Alimentacao", "Mercado x", 100.0);

            List<?> operacoes = (List<?>) campoOperacoes.get(contaCorrente);

            assertEquals(1, operacoes.size());
        }

        @ParameterizedTest(name = "{index} - categoria={0}, descricao={1}, valor={2}")
        @ArgumentsSource(AdicionarOperacaoInvalidaProvider.class)
        @DisplayName("Não adicionar operação com categoria, descrição ou valor inválidos")
        void naoDeveAdicionarOperacaoQuandoDadosInvalidos(String categoria, String descricao,
              Double valor) throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456");

            Class<? extends ContaCorrente> clazz = ContaCorrente.class;

            Field campoOperacoes = clazz.getDeclaredField("operacoes");
            campoOperacoes.trySetAccessible();

            Method adicionarOperacao = clazz.getDeclaredMethod(
                  "adicionarOperacao", String.class, String.class, Double.class
            );

            adicionarOperacao.invoke(contaCorrente, categoria, descricao, valor);

            List<?> operacoes = (List<?>) campoOperacoes.get(contaCorrente);

            assertEquals(0, operacoes.size());
        }

    }

    @Nested
    @DisplayName("ContaCorrente 06. Buscar operações por categoria")
    class BuscarOperacoesPorCategoriaTests {

        @ParameterizedTest(name = "{index} - categoria={1}")
        @ArgumentsSource(BuscarOperacoesPorCategoriaProvider.class)
        @DisplayName("Deve buscar as operações da categoria ignorando maiúsculas e minúsculas")
        void test1(List<Operacao> operacoes, String categoria, List<Operacao> esperadas)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method buscarOperacoesPorCategoria = ContaCorrente.class.getDeclaredMethod(
                  "buscarOperacoesPorCategoria", String.class);

            assertEquals(esperadas, buscarOperacoesPorCategoria.invoke(contaCorrente, categoria));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 07. Buscar operações por valor")
    class BuscarOperacoesPorValorTests {

        @ParameterizedTest(name = "{index} - valor={1}")
        @ArgumentsSource(BuscarOperacoesPorValorProvider.class)
        @DisplayName("Deve buscar as operações com o valor informado")
        void test1(List<Operacao> operacoes, Double valor, List<Operacao> esperadas)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method buscarOperacoesPorValor = ContaCorrente.class.getDeclaredMethod(
                  "buscarOperacoesPorValor", Double.class);

            assertEquals(esperadas, buscarOperacoesPorValor.invoke(contaCorrente, valor));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 08. Buscar operações do tipo saida (valores negativos)")
    class BuscarOperacoesDoTipoSaidaTests {

        @ParameterizedTest(name = "{index} - esperadas={1}")
        @ArgumentsSource(BuscarOperacoesSaidaProvider.class)
        @DisplayName("Deve buscar somente as operações com valor negativo")
        void test1(List<Operacao> operacoes, List<Operacao> esperadas)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method buscarOperacoesSaida = ContaCorrente.class.getDeclaredMethod(
                  "buscarOperacoesSaida");

            assertEquals(esperadas, buscarOperacoesSaida.invoke(contaCorrente));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 09. Buscar operações por descrição")
    class BuscarOperacoesPorDescricaoTests {

        @ParameterizedTest(name = "{index} - descricao={1}")
        @ArgumentsSource(BuscarOperacoesPorDescricaoProvider.class)
        @DisplayName("Deve buscar operações pela descrição (parcial, sem diferenciar maiúsculas)")
        void test1(List<Operacao> operacoes, String descricao, List<Operacao> esperadas)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method buscarOperacoesPorDescricao = ContaCorrente.class.getDeclaredMethod(
                  "buscarOperacoesPorDescricao", String.class);

            assertEquals(esperadas, buscarOperacoesPorDescricao.invoke(contaCorrente, descricao));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 10. Buscar menor valor de operação")
    class BuscarMenorValorOperacaoTests {

        @ParameterizedTest(name = "{index} - menor valor esperado={1}")
        @ArgumentsSource(BuscarMenorValorProvider.class)
        @DisplayName("Deve buscar o menor valor entre as operações (0 quando não há operações)")
        void test1(List<Operacao> operacoes, Double esperado)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method buscarMenorValor = ContaCorrente.class.getDeclaredMethod("buscarMenorValor");

            assertEquals(esperado, buscarMenorValor.invoke(contaCorrente));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 11. Obter saldo")
    class ObterSaldoTests {

        @ParameterizedTest(name = "{index} - saldo esperado={1}")
        @ArgumentsSource(ObterSaldoProvider.class)
        @DisplayName("Deve retornar o saldo final após todas as operações")
        void test1(List<Operacao> operacoes, Double esperado)
              throws ReflectiveOperationException {
            ContaCorrente contaCorrente = ContaCorrenteFactory.getInstance("João", "1234",
                  "123456", operacoes);

            Method obterSaldo = ContaCorrente.class.getDeclaredMethod("obterSaldo");

            assertEquals(esperado, obterSaldo.invoke(contaCorrente));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 12. Obter saldo por categoria")
    class ObterSaldoPorCategoriaTests {

        @ParameterizedTest(name = "{index} - categoria={1}, saldo esperado={2}")
        @ArgumentsSource(ObterSaldoPorCategoriaProvider.class)
        @DisplayName("Deve retornar o saldo (entradas menos saídas) da categoria informada")
        void test1(List<Operacao> operacoes, String categoria, Double esperado)
              throws ReflectiveOperationException {
            ContaCorrente conta = ContaCorrenteFactory.getInstance("João", "1234", "123456",
                  operacoes);

            Method obterSaldoPorCategoria = ContaCorrente.class.getDeclaredMethod(
                  "obterSaldoPorCategoria", String.class);

            assertEquals(esperado, obterSaldoPorCategoria.invoke(conta, categoria));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 13. Buscar categoria com maior gasto")
    class BuscarCategoriaComMaiorGastoTests {

        @ParameterizedTest(name = "{index} - categoria esperada={1}")
        @ArgumentsSource(BuscarCategoriaComMaiorGastoProvider.class)
        @DisplayName("Deve retornar a categoria com a maior soma de saídas")
        void test1(List<Operacao> operacoes, String esperada)
              throws ReflectiveOperationException {
            ContaCorrente conta = ContaCorrenteFactory.getInstance("João", "1234", "123456",
                  operacoes);

            Method buscarCategoriaComMaiorGasto = ContaCorrente.class.getDeclaredMethod(
                  "buscarCategoriaComMaiorGasto");

            assertEquals(esperada, buscarCategoriaComMaiorGasto.invoke(conta));
        }
    }

    @Nested
    @DisplayName("ContaCorrente 14. 🏆 Desafio: Buscar operações duplicadas")
    class BuscarOperacoesDuplicadasTests {

        @ParameterizedTest(name = "{index} - esperadas={1}")
        @ArgumentsSource(BuscarOperacoesDuplicadasProvider.class)
        @DisplayName("Deve retornar as operações duplicadas, cada uma uma vez, na ordem original")
        void test1(List<Operacao> operacoes, List<Operacao> esperadas)
              throws ReflectiveOperationException {
            ContaCorrente conta = ContaCorrenteFactory.getInstance("João", "1234", "123456",
                  operacoes);

            Method buscarOperacoesDuplicadas = ContaCorrente.class.getDeclaredMethod(
                  "buscarOperacoesDuplicadas");

            assertEquals(esperadas, buscarOperacoesDuplicadas.invoke(conta));
        }
    }
}
