package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Page Object para a tela inicial do DocuSign PowerForm (Entrada de Dados).
 * Define localizadores e métodos de preenchimento.
 */
public class DocuSignRecipientPage extends BasePage {

    // --- LOCALIZADORES EXPLÍCITOS DO DOCUSIGN POWERFORM ---

    // Os localizadores são definidos usando o atributo 'name' ou 'aria-label',
    // que geralmente são consistentes com os nomes dos campos definidos no PowerForm.

    // NOME E EMAIL DO FORNECEDOR
    private final By fornecedorNomeField = By.cssSelector("input[name='fornecedorNome']");
    // Path alternativo (Exemplo com XPath): //input[@placeholder='fornecedorNome']
    private final By fornecedorEmailField = By.cssSelector("input[name='fornecedorEmail']");
    // Path alternativo (Exemplo com XPath): //input[@placeholder='fornecedorEmail']

    // NOME E EMAIL DO OBSERVADOR
    private final By observadorNomeField = By.cssSelector("input[name='observadorNome']");
    private final By observadorEmailField = By.cssSelector("input[name='observadorEmail']");

    // NOME E EMAIL DO FORNECEDOR OBSERVADOR (Assumindo que este campo existe com este nome)
    private final By fornecedorObservadorNomeField = By.cssSelector("input[name='fornecedorObservadorNome']");
    private final By fornecedorObservadorEmailField = By.cssSelector("input[name='fornecedorObservadorEmail']");

    // NOME E EMAIL DOS GESTORES (1, 2 e 3)
    private final By gestor1NomeField = By.cssSelector("input[name='gestor1Nome']");
    private final By gestor1EmailField = By.cssSelector("input[name='gestor1Email']");
    private final By gestor2NomeField = By.cssSelector("input[name='gestor2Nome']");
    private final By gestor2EmailField = By.cssSelector("input[name='gestor2Email']");
    private final By gestor3NomeField = By.cssSelector("input[name='gestor3Nome']");
    private final By gestor3EmailField = By.cssSelector("input[name='gestor3Email']");

    // BOTÃO "BEGIN SIGNING" ou "COMEÇAR A ASSINAR"
    private final By beginSigningButton = By.xpath("//button[contains(normalize-space(), 'BEGIN SIGNING') or contains(normalize-space(), 'COMEÇAR A ASSINAR')]");
    // Path alternativo (ID comum): #action-button

    // CHECKBOX "I agree to use electronic..." (Pop-up de acordo)
    private final By agreementCheckbox = By.xpath("//label[contains(normalize-space(), 'I agree to use electronic')]/input[@type='checkbox']");

    // BOTÃO "CONTINUE" (Pop-up)
    private final By continueButton = By.xpath("//button[contains(normalize-space(), 'CONTINUE')]");
    // Path alternativo (ID comum): #signing-start-button

    public DocuSignRecipientPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre a URL do DocuSign PowerForm.
     * @param url A URL do formulário.
     */
    public void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Preenche o formulário completo com base nos dados de uma linha do CSV.
     * Implementa o preenchimento da esquerda para a direita (colunas) e de cima para baixo (linhas)
     * através da iteração do Step Definition.
     * @param data Linha de dados mapeada (chave: nome da coluna, valor: dado).
     */
    public void fillAllRecipientData(Map<String, String> data) {
        System.out.println("--- Preenchendo dados para a linha: " + data.get("fornecedorNome") + " ---");

        // Preenche Nomes e Emails, seguindo a ordem da planilha
        // 1. FORNECEDOR
        fillField(fornecedorNomeField, data.get("fornecedorNome"));
        fillField(fornecedorEmailField, data.get("fornecedorEmail"));

        // 2. OBSERVADOR
        fillField(observadorNomeField, data.get("observadorNome"));
        fillField(observadorEmailField, data.get("observadorEmail"));

        // 3. FORNECEDOR OBSERVADOR
        fillField(fornecedorObservadorNomeField, data.get("fornecedorObservadorNome"));
        fillField(fornecedorObservadorEmailField, data.get("fornecedorObservadorEmail"));

        // 4. GESTOR 1
        fillField(gestor1NomeField, data.get("gestor1Nome"));
        fillField(gestor1EmailField, data.get("gestor1Email"));

        // 5. GESTOR 2
        fillField(gestor2NomeField, data.get("gestor2Nome"));
        fillField(gestor2EmailField, data.get("gestor2Email"));

        // 6. GESTOR 3
        fillField(gestor3NomeField, data.get("gestor3Nome"));
        fillField(gestor3EmailField, data.get("gestor3Email"));

        System.out.println("Dados preenchidos. Esperando 5 segundos para conferência...");
        // Pausa de 5 segundos conforme solicitado
        waitManually(USER_CONFERENCE_WAIT);
    }

    /**
     * Clica no botão "BEGIN SIGNING".
     */
    public void clickBeginSigning() {
        clickElement(beginSigningButton);
    }

    /**
     * Trata o pop-up de Acordo Eletrônico.
     * - Clica no checkbox.
     * - Clica no botão "CONTINUE".
     */
    public void handleAgreementPopup() {
        System.out.println("Pop-up de acordo apareceu. Esperando 5 segundos para conferência...");
        // Pausa de 5 segundos conforme solicitado
        waitManually(USER_CONFERENCE_WAIT);

        // 1. Clica no Checkbox
        clickElement(agreementCheckbox);

        // 2. Clica em Continue
        clickElement(continueButton);
    }
}
