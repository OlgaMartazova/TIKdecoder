import java.util.Arrays;
import java.util.stream.IntStream;

public class BWDecoder {
    static String decodeBW(String text, int bwNumber) {
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
        return (int) IntStream.range(0, indexOfChar).filter(i -> text.charAt(i) == ch).count();
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
