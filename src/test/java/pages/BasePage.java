package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe Base para todas as Page Objects, implementando o Princípio da Responsabilidade Única (SRP)
 * para gerenciamento do WebDriver e métodos de interação básicos.
 */

public class BasePage {

    public static WebDriver driver;

    // Constante para o tempo de espera explícita
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Tempo de espera solicitado pelo usuário para conferência.
    protected static final long USER_CONFERENCE_WAIT = 5000;

    // Construtor para inicialização do Page Object
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }

    /**
     * Inicializa o WebDriver (Chrome) utilizando WebDriverManager para portabilidade.
     */

    public static void setup() {
        //
        // --- INICIALIZAÇÃO IMPLÍCITA (RECOMENDADA) ---
        // Configura e baixa o driver automaticamente (Abordagem via internet)

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // --- INICIALIZAÇÃO EXPLÍCITA (SE NECESSÁRIO) ---
        /*
        // Para usar a chamada explícita de uma pasta Windows:
        // System.setProperty("webdriver.chrome.driver", "C:\\caminho\\para\\o\\chromedriver.exe");
           System.setProperty("webdriver.edge.driver", "C:\\Users\\joaoc\\Drivers\\edgedriver_win64\\msedgedriver.exe");
        // driver = new ChromeDriver();
        */

        // Define a espera implícita
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    /**
     * Fecha o navegador.
     */
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navega para a URL especificada.
     * Este método é fundamental e pertence à BasePage.
     * @param url A URL a ser aberta.
     */
    public void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Encontra um elemento e espera que ele esteja visível.
     * @param locator O localizador do elemento.
     * @return O WebElement encontrado.
     */
    protected WebElement waitForElementVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Encontra um elemento e espera que ele possa ser clicado.
     * @param locator O localizador do elemento.
     * @return O WebElement encontrado.
     */
    protected WebElement waitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Preenche um campo de texto.
     * @param locator O localizador do campo.
     * @param text O texto a ser digitado.
     */
    public void fillField(By locator, String text) {
        WebElement element = waitForElementVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clica em um elemento.
     * @param locator O localizador do elemento.
     */
    public void clickElement(By locator) {
        WebElement element = waitForElementClickable(locator);
        element.click();
    }

    /**
     * Realiza uma pausa no thread para conferência manual (conforme solicitado).
     * @param milliseconds O tempo de pausa em milissegundos.
     */
    public void waitManually(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}