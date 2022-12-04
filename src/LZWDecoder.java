import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LZWDecoder {
    public static void main(String[] args) throws IOException {
        StringBuilder code = new StringBuilder();
//        String decimalCode = "0 1 2 3 6 1 0";
        String decimalCode = "1 2 0 4 1 3";
        for (int number : Arrays
                .stream(decimalCode.split("\\s"))
                .mapToInt(Integer::parseInt)
                .toArray()
        ) {
            code.append(toCode(number));
        }
//        System.out.println(code);
        Map<String, String> table = getDictFromFile("input.txt");
        System.out.println(code);
        String decodedText = decodeLZW(code.toString(), table);
//        System.out.println(decodedText);
        Scanner sc = new Scanner(System.in);
        int bwNumber = sc.nextInt();
        String output = decodeBW(decodedText, bwNumber);
        System.out.println(output);
    }

    public static Map<String, String> getDictFromFile(String fileName) throws IOException {
        Map<String, String> dictionary = new HashMap<>();
        int codeNumber = 0;
        for (String letter : new BufferedReader(new FileReader(fileName))
                .readLine().split("\\s")) {
            dictionary.put(toCode(codeNumber++), letter);
        }
        return dictionary;
    }

    private static String toCode(int codeNumber) {
        StringBuilder code = new StringBuilder(Integer.toBinaryString(codeNumber));
        return "00000000".substring(0, 8 - code.length()) + code;
    }

    private static String decodeLZW(String codeSequence, Map<String, String> dictionary) {
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
        for (int i = 1; i < codeList.size(); i++) {
            String currentCode = codeList.get(i);
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

    private static String decodeBW(String text, int bwNumber) {
        int indexInSorted = --bwNumber;
        StringBuilder output = new StringBuilder();
        String sortedText = sortString(text);
        for (int i = 0; i < text.length(); i++) {
            output.append(sortedText.charAt(indexInSorted));
            int index = getIndexOfOccurrence(sortedText, indexInSorted);
            indexInSorted = getIndexInText(text, sortedText.charAt(indexInSorted), index);
        }
        return output.toString();
    }

    private static String sortString(String text) {
        char[] chars = text.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

    private static int getIndexOfOccurrence(String text, int indexOfChar) {
        char ch = text.charAt(indexOfChar);
        int count = 0;
        for (int i = 0; i < indexOfChar; i++) {
            if (text.charAt(i) == ch) count++;
        }
        return count;
    }

    private static int getIndexInText(String text, char ch, int indexOfOccurrence) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ch) {
                if (count == indexOfOccurrence) {
                    return i;
                }
                count++;
            }
        }
        return text.length() - 1;
    }
}
