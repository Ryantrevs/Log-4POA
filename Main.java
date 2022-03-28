import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.regex.*;
import java.util.Collection.*;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws IOException {

        try{
            if(args.length<3)
                throw new IOException();

            BufferedReader reader = ReaderFile(args[0],args[1]);

            String line;

            Pattern p = Pattern.compile("\"GET([^\n])*");

            List<String> ListFrequency = new ArrayList();

            while((line = reader.readLine())!= null){
                String ContentFormmated = MatcherPattern(p,line);
                ListFrequency.add(ContentFormmated);
            }
            
            Map<String,Integer> map = BuildFrequency(ListFrequency);

            Map<String, Integer> sorted =
                map.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(
                        Map.Entry::getKey, 
                        Map.Entry::getValue, 
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            
            //List<Entry<String,Integer>> lista = Arrays.asList(sorted);
        
            BuildResult(sorted,Integer.parseInt(args[2]));

        }
        catch(IOException exception){
            System.out.println(ShowMessageInvalid());
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        finally{
            return;
        }

        
    }

    private static void BuildResult(Map<String, Integer> map, int n) throws UnsupportedEncodingException{
        int i = 0;
		try{

            Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("filename.txt"), "utf-8"));

            FileWriter fileWriter = new FileWriter("resultado.txt");

            for (Map.Entry<String,Integer> pair : map.entrySet()) {
                
                if(i>=n){
                    fileWriter.flush();
                    fileWriter.close();
                    return;
                }   
                
                i++;

                String value = pair.getKey();
                fileWriter.write(value +" || Acessado - "+ pair.getValue() + " vezes\n");
            }

            fileWriter.flush();
            fileWriter.close();
        }
        catch(Exception e){
            throw new UnsupportedEncodingException("Ocorreu um erro ao escrever o arquivo");
        }
    }

    private static void WriteResult(Writer writer, String content){
        try{
            System.out.println("conteudo "+content);
            writer.write(content);
        }
        catch(Exception e){
            System.out.println("error ao escrever no arquivo");
        }
        
        return;
    }

    private static Map<String,Integer> BuildFrequency(List<String> list){
        Map<String,Integer> hshmap = new HashMap<String, Integer>(); 
		for (String str : list) 
		{ 
			if (hshmap.keySet().contains(str)) 
				hshmap.put(str, hshmap.get(str) + 1); 
			else
				hshmap.put(str, 1);
		}

        return hshmap;
    }

    private static String MatcherPattern(Pattern pattern,String content){
        Matcher m = pattern.matcher(content);
        
        if(m.find())
            return m.group();
        
        return "";
    }

    private static BufferedReader ReaderFile(String path, String filename) throws FileNotFoundException{
        FileReader fileReader = new FileReader(path+filename);
        BufferedReader reader = new BufferedReader(fileReader);
        return reader;
    }

    private static String ShowMessageInvalid() {
		return "Digite dois argumentos validos:\n1-Caminho do arquivo,\n2- Nome do arquivo,\n3- A quantidade de resultados desejada.";
	}
}