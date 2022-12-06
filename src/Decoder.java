import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Decoder {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader("input.txt"));
        String code = input.readLine();
        int bwNumber = Integer.parseInt(input.readLine());

        Map<String, String> table = LZWDecoder.getDictFromFile("alphabet.txt");
        String decodedText = LZWDecoder.decodeLZW(code, table);
//        String decodedText = "КСАУКТ";
//        String decodedText = "ткммтмтиаеаеаа";
//        String decodedText = "аамлмрыауа__мм";
        String output = BWDecoder.decodeBW(decodedText, bwNumber);
        System.out.println("Получено сообщение:");
        System.out.println(output);
    }
}
