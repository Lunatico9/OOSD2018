package it.bibliotecadigitale.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.*;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Highlighter {

    private String regex;
    private String htmlContent;
    Pattern pat;
    Matcher mat;


    public Highlighter(String searchString, String htmlString) {
        regex = buildRegexFromQuery(searchString);
        htmlContent = htmlString;
        pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Ottengo l'html finale con i relativi tag html che evidenziano la parola passata
     * @return String
     */
    @SuppressWarnings("deprecation")
	public String getHighlightedHtml() {

        Document doc = (Document) Jsoup.parse(htmlContent);

        final List<TextNode> nodesToChange = new ArrayList<TextNode>();

        NodeTraversor nd  = new NodeTraversor(new NodeVisitor() {

            @Override
            public void tail(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    String text = textNode.getWholeText();

                    mat = pat.matcher(text);

                    if(mat.find()) {
                        nodesToChange.add(textNode);
                    }
                }
            }

            @Override
            public void head(Node node, int depth) {        
            }
        });

        nd.traverse(((org.jsoup.nodes.Document) doc).body());

        for (TextNode textNode : nodesToChange) {
            Node newNode = buildElementForText(textNode);
            textNode.replaceWith(newNode);
        }
        return doc.toString();
    }

    /**
     * Costruisco la regex finale contenente la parola passata
     * @param String queryString
     * @return String
     */
    private static String buildRegexFromQuery(String queryString) {
        String regex = "";
        String queryToConvert = queryString;

        /* Clean up query */

        queryToConvert = queryToConvert.replaceAll("[\\p{Punct}]*", " ");
        queryToConvert = queryToConvert.replaceAll("[\\s]*", " ");

        String[] regexArray = queryString.split(" ");

        regex = "(";
        for(int i = 0; i < regexArray.length - 1; i++) {
            String item = regexArray[i];
            regex += "(\\b)" + item + "(\\b)|";
        }

        regex += "(\\b)" + regexArray[regexArray.length - 1] + "[a-zA-Z0-9]*?(\\b))";
        return regex;
    }

    /**
     * Costruisco il nodo elemento Testo
     * @param TextNode textNode
     * @return Node
     */
    @SuppressWarnings("deprecation")
	private Node buildElementForText(TextNode textNode) {
        String text = textNode.getWholeText().trim();

        ArrayList<MatchedWord> matchedWordSet = new ArrayList<MatchedWord>();

        mat = pat.matcher(text);

        while(mat.find()) {
            matchedWordSet.add(new MatchedWord(mat.start(), mat.end()));
        }

        StringBuffer newText = new StringBuffer(text);

        for(int i = matchedWordSet.size() - 1; i >= 0; i-- ) {
            String wordToReplace = newText.substring(matchedWordSet.get(i).start, matchedWordSet.get(i).end);
            wordToReplace = "<span style=\"background-color:yellow;\">" + wordToReplace+ "</span>";
            newText = newText.replace(matchedWordSet.get(i).start, matchedWordSet.get(i).end, wordToReplace);       
        }
        return new DataNode(newText.toString(), textNode.baseUri());
    }

    class MatchedWord {
        public int start;
        public int end;

        public MatchedWord(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}