package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import org.junit.Assert;
import pages.BasePage;
import pages.DocuSignRecipientPage;
import utils.CsvDataReader;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Classe que contém as definições dos passos do Cucumber.
 */
public class DocuSignSteps {

    private DocuSignRecipientPage docuSignPage;
    private CsvDataReader         dataReader;
    private final String          DOCUSIGN_URL = "https://powerforms.docusign.net/75ad6ab6-2f7e-4fcb-a9ac-54f9aeefde40?env=na2&acct=68f26f24-91ee-4170-9a61-75e6ad8cebab&accountId=68f26f24-91ee-4170-9a61-75e6ad8cebab";

    /**
     * Hook do Cucumber que é executado antes de cada cenário.
     * Configura o WebDriver e inicializa o Page Object.
     */
    @Before
    public void setupTest() {
        BasePage.setup();
        docuSignPage = new DocuSignRecipientPage(BasePage.driver);

        //
        // Inicializa o leitor de dados para ser usado nos passos

        dataReader = new CsvDataReader();
    }

    /**
     * Hook do Cucumber que é executado depois de cada cenário.
     * Fecha o WebDriver.
     */

    @After
    public void teardownTest() {
        BasePage.teardown();
    }

    @Dado("^que a URL do DocuSign PowerForm está aberta$")
    public void queAURLDoDocuSignPowerFormEstaAberta() {

        docuSignPage.openUrl(DOCUSIGN_URL);

        //
        // Usando Hamcrest para verificar se a URL foi carregada corretamente (Princípio OCP do SOLID - aberta para extensão, fechada para modificação)

        Assert.assertThat("A URL do DocuSign não foi aberta corretamente.",
                BasePage.driver.getCurrentUrl(),
                containsString("docusign.net"));
        System.out.println("URL DocuSign aberta com sucesso.");
    }

    @Quando("^os dados da planilha \"([^\"]*)\" são lidos e iterados$")
    public void osDadosDaPlanilhaSaoLidosEIterados(String fileName) {
        // Nada a fazer aqui. A leitura de dados é preparada no @Before e a iteração é feita no próximo passo.
        System.out.println("Leitura de dados do arquivo " + fileName + " preparada.");
    }

    @Quando("^para cada linha de dados, o formulário é preenchido e o botão 'Begin Signing' é clicado$")
    public void paraCadaLinhaDeDadosOFormularioEPreenchidoEOBotaoBeginSigningEClicado() {
        List<String[]> rows = dataReader.getDataRows();
        int rowCount = 0;

        // Implementação do Looping 'while' e validação de linha não-branca/nula.
        // A iteração com 'while' é feita manualmente no índice.
        while (rowCount < rows.size()) {
            String[] currentRow = rows.get(rowCount);

            // Verifica se a linha tem dados válidos (campos diferentes de brancos ou nulos)
            if (dataReader.isRowValid(currentRow)) {
                Map<String, String> rowDataMap = dataReader.getRowDataMap(currentRow);

                // 1. Preenche o Formulário
                docuSignPage.fillAllRecipientData(rowDataMap);

                // 2. Clica no Botão "Begin Signing"
                docuSignPage.clickBeginSigning();

                // Para o escopo deste cenário (apenas a tela de entrada), saímos do loop após a primeira linha
                // para evitar múltiplas execuções indesejadas na mesma sessão.
                // Em um cenário real de carga, este 'break' seria removido.
                break;
            }
            rowCount++;
        }

        Assert.assertThat("Nenhuma linha de dados válida foi encontrada no CSV.", rowCount, lessThan(rows.size()));
    }

    @Entao("^o pop-up de acordo eletrônico é exibido e aceito$")
    public void oPopUpDeAcordoEletronicoEDisplayadoEAceito() {
        // 1. Manipula o pop-up de acordo (clica no checkbox e continua)
        docuSignPage.handleAgreementPopup();
        System.out.println("Pop-up de acordo aceito. Navegando para a tela de assinatura.");

        // Asserção (Hamcrest) para verificar que a URL mudou após a interação
        Assert.assertThat("A navegação para a tela de assinatura falhou.",
                BasePage.driver.getCurrentUrl(),
                not(containsString("powerforms.docusign.net")));
    }
}
