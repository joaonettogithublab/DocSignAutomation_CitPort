package runner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Classe Runner para execução dos testes Cucumber via JUnit 4.
 * Gera relatórios Maven (Surefire) e Cucumber Reports.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/DocuSignFeature.feature",
        glue = {"steps"},

        //
        // CORREÇÃO: Usando o operador lógico 'and' para combinar as tags.
        // Se desejar executar se tiver QUALQUER UMA das tags, use 'or'.

        tags = "@DocuSign and @CargaDeDados",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/junit.xml"
        },
        monochrome = true
)
public class TestRunner { // Alterado para 'public class' (Boa Prática)
        // Esta classe fica vazia. As anotações acima configuram a execução.
        // O JUnit irá rodar esta classe, que por sua vez, irá rodar o Cucumber.
}