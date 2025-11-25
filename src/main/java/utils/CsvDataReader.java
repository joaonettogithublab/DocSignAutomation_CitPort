package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para leitura do arquivo CSV de dados.
 * O arquivo deve estar na raiz do projeto ou ser ajustado o caminho no construtor.
 */
public class CsvDataReader {

    // Lista de listas de strings para armazenar todos os dados lidos
    private List<String[]> dataRows;

    // O cabeçalho da planilha
    private String[] header;

    // Caminho do arquivo de dados (assumindo que o arquivo está na raiz do projeto)
    private static final String DATA_FILE_PATH = "CargaDados_Docsign.xlsx - Sheet1.csv";

    public CsvDataReader() {
        try {

            // Inicializa o leitor de CSV
            CSVReader reader = new CSVReader(new FileReader(DATA_FILE_PATH));

            // Lê todas as linhas do arquivo de uma vez
            List<String[]> allRows = reader.readAll();

            if (!allRows.isEmpty()) {

                // A primeira linha é o cabeçalho
                this.header = allRows.get(0);

                // As linhas de dados (começando da segunda linha)
                this.dataRows = allRows.subList(1, allRows.size());
            } else {
                this.header = new String[0];
                this.dataRows = new ArrayList<>();
            }
            reader.close();

        } catch (IOException | CsvException e) {
            System.err.println("ERRO ao ler o arquivo CSV: " + DATA_FILE_PATH);
            e.printStackTrace();
            this.header = new String[0];
            this.dataRows = new ArrayList<>();
        }
    }

    /**
     * Retorna a lista de todas as linhas de dados.
     * @return Lista de arrays de strings, onde cada array é uma linha de dados.
     */
    public List<String[]> getDataRows() {
        return this.dataRows;
    }

    /**
     * Retorna a lista de nomes das colunas (cabeçalho).
     * @return Array de strings com os nomes das colunas.
     */
    public String[] getHeader() {
        return this.header;
    }

    /**
     * Verifica se a linha atual contém dados válidos (não nulos/vazios em todos os campos).
     * @param row O array de strings que representa a linha.
     * @return true se a linha tem pelo menos um campo não vazio, false caso contrário.
     */
    public boolean isRowValid(String[] row) {
        if (row == null || row.length == 0) {
            return false;
        }
        // Itera sobre todos os campos da linha
        for (String cell : row) {
            // Verifica se o campo não é nulo e se não está vazio/contém apenas espaços
            if (cell != null && !cell.trim().isEmpty()) {
                return true; // Pelo menos um campo tem informação
            }
        }
        return false; // Todos os campos estão vazios ou nulos
    }

    /**
     * Retorna um mapa (chave: nome_da_coluna, valor: dado_da_linha) para uma linha específica.
     * @param row A linha de dados.
     * @return Mapa de dados da linha.
     */
    public java.util.Map<String, String> getRowDataMap(String[] row) {
        java.util.Map<String, String> dataMap = new java.util.HashMap<>();
        if (row.length != header.length) {
            System.err.println("A linha de dados tem um número diferente de colunas do cabeçalho.");
            return dataMap;
        }
        for (int i = 0; i < header.length; i++) {
            dataMap.put(header[i], row[i]);
        }
        return dataMap;
    }
}