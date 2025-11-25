package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

/**
 * Page Object para a primeira tela (Recipient Information) do DocuSign PowerForm.
 *
 * NOTA: Os localizadores são definidos usando XPath baseado no atributo 'name',
 * que é a forma mais robusta de interagir com campos de formulário dinâmicos
 * do DocuSign, uma vez que a inspeção direta da página está inacessível.
 */

public class DocuSignRecipientPage extends BasePage {

    // ==============================================================================================
    // 1. LOCALIZADORES DE CAMPOS DE ENTRADA (Input Fields)
    // ==============================================================================================
    //
    // Localizadores redefinidos para usar o atributo 'name'

    private final By fornecedorNomeInput = By.xpath("//input[@name='fornecedorNome']");
    private final By fornecedorEmailInput = By.xpath("//input[@name='fornecedorEmail']");
    private final By observadorNomeInput = By.xpath("//input[@name='observadorNome']");
    private final By observadorEmailInput = By.xpath("//input[@name='observadorEmail']");
    private final By fornecedorObservadorNomeInput = By.xpath("//input[@name='fornecedorObservadorNome']");
    private final By fornecedorObservadorEmailInput = By.xpath("//input[@name='fornecedorObservadorEmail']");
    private final By gestor1NomeInput = By.xpath("//input[@name='gestor1Nome']");
    private final By gestor1EmailInput = By.xpath("//input[@name='gestor1Email']");
    private final By gestor2NomeInput = By.xpath("//input[@name='gestor2Nome']");
    private final By gestor2EmailInput = By.xpath("//input[@name='gestor2Email']");
    private final By gestor3NomeInput = By.xpath("//input[@name='gestor3Nome']");
    private final By gestor3EmailInput = By.xpath("//input[@name='gestor3Email']");

    // ==============================================================================================
    // 2. LOCALIZADORES DE BOTÕES E POP-UP
    // ==============================================================================================
    //
    // Botão "BEGIN SIGNING" na tela de Recipient Information

    private final By beginSigningButton = By.xpath("//button[contains(text(), 'Begin Signing')]");

    //
    // Checkbox e Botão no pop-up de Acordo Eletrônico (Electronic Record and Signature Disclosure)
    private final By agreementCheckbox = By.id("ds-disclosure-consent-checkbox");
    private final By continueButton = By.xpath("//button[text()='Continue']");

    /**
     * Construtor que recebe a instância do WebDriver.
     * @param driver A instância do WebDriver.
     */

    public DocuSignRecipientPage(WebDriver driver) {
        super(driver);
    }

    // ==============================================================================================
    // 3. MÉTODOS DE AÇÃO (Actions)
    // ==============================================================================================

    /**
     * Preenche todos os campos de dados do destinatário usando o mapa de dados do CSV.
     * Implementa uma pausa de 5 segundos após a interação com cada campo.
     *
     * @param data Mapa de dados da linha CSV com chaves correspondentes aos nomes dos campos (ex: 'fornecedorNome').
     */

    public void fillAllRecipientData(Map<String, String> data) {
        System.out.println("Preenchendo dados do formulário com a primeira linha do CSV...");

        //
        // Usamos um método auxiliar para preencher e pausar

        fillFieldWithPause(fornecedorNomeInput, data.getOrDefault("fornecedorNome", ""));
        fillFieldWithPause(fornecedorEmailInput, data.getOrDefault("fornecedorEmail", ""));

        fillFieldWithPause(observadorNomeInput, data.getOrDefault("observadorNome", ""));
        fillFieldWithPause(observadorEmailInput, data.getOrDefault("observadorEmail", ""));

        fillFieldWithPause(fornecedorObservadorNomeInput, data.getOrDefault("fornecedorObservadorNome", ""));
        fillFieldWithPause(fornecedorObservadorEmailInput, data.getOrDefault("fornecedorObservadorEmail", ""));

        fillFieldWithPause(gestor1NomeInput, data.getOrDefault("gestor1Nome", ""));
        fillFieldWithPause(gestor1EmailInput, data.getOrDefault("gestor1Email", ""));

        fillFieldWithPause(gestor2NomeInput, data.getOrDefault("gestor2Nome", ""));
        fillFieldWithPause(gestor2EmailInput, data.getOrDefault("gestor2Email", ""));

        fillFieldWithPause(gestor3NomeInput, data.getOrDefault("gestor3Nome", ""));
        fillFieldWithPause(gestor3EmailInput, data.getOrDefault("gestor3Email", ""));

        System.out.println("Todos os 12 campos de destinatário preenchidos.");
    }

    /**
     * Método auxiliar para preencher um campo de texto e aplicar uma pausa de 5 segundos.
     * @param locator O localizador By do campo.
     * @param value O valor a ser preenchido.
     */

    private void fillFieldWithPause(By locator, String value) {
        if (value != null && !value.trim().isEmpty()) {
            waitForElementToBeVisible(locator);
            driver.findElement(locator).sendKeys(value);
            sleep(5000); // Pausa de 5 segundos solicitada.
        }
    }

    /**
     * Clica no botão "BEGIN SIGNING".
     */

    public void clickBeginSigning() {
        System.out.println("Clicando no botão 'Begin Signing'.");
        driver.findElement(beginSigningButton).click();
        sleep(5000); // Pausa de 5 segundos solicitada após o clique.
    }

    /**
     * Manipula o pop-up de Acordo Eletrônico (aceita o acordo e continua).
     * Implementa pausas de 5 segundos conforme solicitado.
     */

    public void handleAgreementPopup() {

        // 1. Espera o Checkbox do Acordo aparecer (pode levar algum tempo)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(agreementCheckbox));

        // 2. Clica no Checkbox
        System.out.println("Aceitando o termo de Acordo Eletrônico.");
        driver.findElement(agreementCheckbox).click();
        sleep(5000); // Pausa de 5 segundos após o clique no Checkbox.

        // 3. Clica no botão 'Continue'
        System.out.println("Clicando em 'Continue'.");
        driver.findElement(continueButton).click();
        sleep(5000); // Pausa de 5 segundos após o clique no botão 'Continue'.
    }

    /**
     * Método genérico para aguardar que um elemento esteja visível.
     * @param locator O localizador By do elemento.
     */
    private void waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Pausa a execução do thread.
     * @param milliseconds Duração da pausa em milissegundos.
     */

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}