package mx.aluras.literatura.gutendex.client;

import org.yaml.snakeyaml.internal.Logger;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GeminiApiClient {

    private static final String MODELO = "gemini-2.0-flash-lite";
    private static final String FORMATO_PROMPT = "Traduce el siguiente texto al idioma '%s'. Luego, resume el contenido en máximo 200 caracteres. Si no puedes hacerlo, crea una versión recortada obligatoriamente. Devuelve solo el resultado entre comillas dobles: \"%s\"";

    private static final Logger logger = Logger.getLogger(GeminiApiClient.class.getName());

    public static String getTranslate(String texto, String language, String apiKey) {
        if (texto == null || texto.isEmpty() || language == null || language.isEmpty()) {
            return texto;
        }

        String prompt = String.format(FORMATO_PROMPT, language, texto);

        try {
            Client cliente = new Client.Builder().apiKey(apiKey).build();
            GenerateContentResponse response = cliente.models.generateContent(MODELO, prompt, null);
            String rawText = (response != null && !response.text().isEmpty()) ? response.text() : texto;
            return extractQuotedText(rawText);

        } catch (Exception e) {
            logger.warn(" Traduccion de Gemini: " + e.getMessage());
            if (texto.length() > 200) {
                texto = texto.substring(0, 200);
            }
            return texto;
        }
    }

    private static String extractQuotedText(String input) {
        int firstQuote = input.indexOf('"');
        int lastQuote = input.lastIndexOf('"');

        if (firstQuote != -1 && lastQuote != -1 && firstQuote < lastQuote) {
            input = input.substring(firstQuote + 1, lastQuote);
            if (input.length() > 200) {
                input = input.substring(0, 200);
            }
            return input;
        }
        if (input.length() > 200) {
            input = input.substring(0, 200);
        }
        return input;
    }

}
