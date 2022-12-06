import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LZWDecoder {
    public static Map<String, String> getDictFromFile(String fileName) throws IOException {
        Map<String, String> dictionary = new HashMap<>();
        int codeNumber = 0;
        for (String letter : new BufferedReader(new FileReader(fileName))
                .readLine().split("")) {
            dictionary.put(toCode(codeNumber++), letter);
        }
        return dictionary;
    }

    static String toCode(int codeNumber) {
        StringBuilder code = new StringBuilder(Integer.toBinaryString(codeNumber));
        return "00000000".substring(0, 8 - code.length()) + code;
    }

    static String decodeLZW(String codeSequence, Map<String, String> dictionary) {
        int codeNumber = dictionary.size();
        String BOARDER = "";
        List<String> codeList = new ArrayList<>();
        for (int i = 0; i < codeSequence.length(); i += 8) {
            codeList.add(codeSequence.substring(i, i + 8));
        }
        StringBuilder output = new StringBuilder();
        String old = codeList.get(0);
        String maxPrefix = dictionary.get(old);
        String first = maxPrefix.substring(0, 1);
        output.append(maxPrefix).append(BOARDER);
        String currentCode;
        for (int i = 1; i < codeList.size(); i++) {
            currentCode = codeList.get(i);
            if (!dictionary.containsKey(currentCode)) {
                maxPrefix = dictionary.get(old) + first;
            } else {
                maxPrefix = dictionary.get(currentCode);
            }
            output.append(maxPrefix).append(BOARDER);
            first = maxPrefix.substring(0, 1);
            dictionary.put(toCode(codeNumber++), dictionary.get(old) + first);
            old = currentCode;
        }
        return output.toString();
    }
}
