package mrsoftware.noter.services;

import java.util.ArrayList;
import java.util.HashMap;

public class InputParser {
    private HashMap<String, String[]> prefixMap = new HashMap<>();
    private HashMap<Character, String[]> inlineMap = new HashMap<Character, String[]>();

    private String[] tokens = {"#", "**"};
    public InputParser() {
        prefixMap.put("#", new String[] {"<h1 style=\"display:inline\">", "</h1>\n"});
        prefixMap.put("##", new String[] {"<h2 style=\"display:inline\">", "</h2>\n"});
        prefixMap.put("###", new String[] {"<h3 style=\"display:inline\">", "</h3>\n"});
        prefixMap.put("####", new String[] {"<h4 style=\"display:inline\">", "</h4>\n"});
        prefixMap.put("]", new String[] {"&#8227;", ""});
        prefixMap.put("]]", new String[] {"   &#8227;", ""});
        prefixMap.put("]]]", new String[] {"      &#8227;", ""});
        prefixMap.put("\\", new String[] {"", ""});

        inlineMap.put('*', new String[] {"<b>", "</b>"});
        inlineMap.put('^', new String[] {"<i>", "</i>"});
    }


    public String parseText(String text) {
        String output = "<!DOCTPYE html>\n<head>\n<meta charset=\"utf-8\">\n</head>\n<body>\n<span style=\"white-space: pre-wrap\">\n<div>\n";
        String[] lines = text.split("\\r?\\n");

        for (String line: lines) {
            String startString = "";
            if (!line.isEmpty()) {
                startString = String.valueOf(line.charAt(0));
            }

            if (line.isEmpty()) {
                output += "\n";
            }
            else if (prefixMap.containsKey(startString)) {
                String newLine = processPrefix(line, startString);
                output += newLine;
            }
            else {
                output += inlineFormatProcessor(line);
                output += "\n";
            }
        }
        output += "</div>\n</span>\n</body>";
        return output;
    }

    private String processPrefix(String line, String prefix) {
        int i = 0;
        String fullPrefix = "";
        String outputLine = "";
        while (line.charAt(i) == prefix.charAt(0)) {
            fullPrefix += line.charAt(i);
            i++;
        }

        outputLine += prefixMap.get(fullPrefix)[0];
        outputLine += inlineFormatProcessor(line.substring(i));
        outputLine += prefixMap.get(fullPrefix)[1];
        outputLine += "\n";

        return outputLine;
    }

    private String inlineFormatProcessor(String line) {
        ArrayList<Character> symbolStack = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        char[] lineCharacters = line.toCharArray();
        for (Character symbol: lineCharacters) {


            if (inlineMap.containsKey(symbol)) {
                if (!symbolStack.isEmpty()) {
                    Character stackSymbol = symbolStack.get(symbolStack.size()-1);
                    if (stackSymbol == '\\') {
                        output.append(symbol);
                    }

                    else if (symbol == stackSymbol) {
                        symbolStack.remove(symbolStack.size()-1);
                        output.append(inlineMap.get(symbol)[1]);
                    }
                    else {
                        symbolStack.add(symbol);
                        output.append(inlineMap.get(symbol)[0]);
                    }


                } else {
                    symbolStack.add(symbol);
                    output.append(inlineMap.get(symbol)[0]);
                }

            } else {
                if (symbol == '\\') {
                    symbolStack.add(symbol);
                } else {
                    output.append(symbol);
                }
            }
        }


        return output.toString();

    }
}
