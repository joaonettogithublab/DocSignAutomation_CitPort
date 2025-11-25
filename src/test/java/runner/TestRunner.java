package runner;

import io.cucumber.junit.CucumberOptions; // Novo import
import io.cucumber.junit.Cucumber; // Novo import
import org.junit.runner.RunWith;

/**
 * Classe Runner para execução dos testes Cucumber via JUnit 4.
 * Gera relatórios Maven (Surefire) e Cucumber Reports.
 */

@RunWith(Cucumber.class)
@CucumberOptions( // Atualizado para io.cucumber.junit.CucumberOptions
        features = "src/test/resources/docsign.feature", // Caminho para os arquivos .feature
        glue = {"steps"}, // Caminho para as Step Definitions

        tags = "@DocuSign, @CargaDeDados", // Tags de Cenários a serem executados
        plugin = { // Formato 'format' foi substituído por 'plugin' em versões mais novas
                "pretty", // Output formatado no console
                "html:target/cucumber-reports/html", // Relatório HTML do Cucumber
                "json:target/cucumber-reports/cucumber.json", // Relatório JSON para ferramentas externas
                "junit:target/cucumber-reports/junit.xml" // Relatório JUnit (para Maven Reports/Surefire)
        },
        monochrome = true // Remove caracteres estranhos do console (Windows)
)
class TestRunner {
        // Esta classe fica vazia. As anotações acima configuram a execução.
        // O JUnit irá rodar esta classe, que por sua vez, irá rodar o Cucumber.
}
