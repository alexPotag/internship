package DeadLinks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException {
		//на вход поступает ссылка
		Scanner scanner = new Scanner(System.in);
		System.out.println("Вставьте ссылку");
		String url = scanner.nextLine();
		//https://stud.lms.tpu.ru/login/index.php пример ссылки
		deadLinks(url); //вызов метода
		scanner.close();
	}
	// метод, собирающий все битые ссылки вместе в одну строку 
	public static void deadLinks(String urlOfSite) throws IOException {
		//чтение html-кода сайта
		URL url = new URL(urlOfSite);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String deadLinks = "";//здесь все ссылки
        
        String inputLine;
        Matcher matcher;
        Pattern pattern = Pattern.compile("(?<=(?i)href\\s{0,1}=\\s{0,1}\").*?(?=\")");//регулярное выражение для выявления ссылки
        while ((inputLine = in.readLine()) != null) {//проходим по всей странице
        	matcher = pattern.matcher(inputLine);
            while (matcher.find()) { // пока есть совпадение
            	String link = inputLine.substring(matcher.start(), matcher.end()); //очередная найденная ссылка
            	if (link.contains("http") == false) {continue;} //отбрасываем, если не нет подстроки http
            	if (isExist(link) == 404) {deadLinks += link + "\n";} //определяем, существует ли страница и сохраняем ее, если не сущ-ет
            	System.out.println(isExist(link) + " " + link); // выводим
            }
        }
        in.close();
        
        if (deadLinks == "")
        	System.out.println("недействительных ссылок не обнаружено");
        else
        	System.out.println("\nНедействительные ссылки:");
        	System.out.println(deadLinks);
	}
	
	// метод, который выясняет, существует ли страница
	public static int isExist(String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString); // создается url на основе строки
	    	HttpURLConnection huc =  (HttpURLConnection)url.openConnection(); // установка соединения
		huc.setRequestMethod("GET"); 
	    	huc.connect(); 
	    	return huc.getResponseCode(); // непосредственно определение 
	}
}
