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
		//íà âõîä ïîñòóïàåò ññûëêà
		Scanner scanner = new Scanner(System.in);
		System.out.println("Âñòàâüòå ññûëêó");
		String url = scanner.nextLine();
		//https://stud.lms.tpu.ru/login/index.php ïðèìåð ññûëêè
		deadLinks(url); //âûçîâ ìåòîäà
		scanner.close();
	}
	// ìåòîä, ñîáèðàþùèé âñå áèòûå ññûëêè âìåñòå â îäíó ñòðîêó 
	public static void deadLinks(String urlOfSite) throws IOException {
		//÷òåíèå html-êîäà ñàéòà
		URL url = new URL(urlOfSite);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String deadLinks = "";//çäåñü âñå ññûëêè
        
        String inputLine;
        Matcher matcher;
        Pattern pattern = Pattern.compile("(?<=(?i)href\\s{0,1}=\\s{0,1}\").*?(?=\")");//ðåãóëÿðíîå âûðàæåíèå äëÿ âûÿâëåíèÿ ññûëêè
        while ((inputLine = in.readLine()) != null) {//ïðîõîäèì ïî âñåé ñòðàíèöå
        	matcher = pattern.matcher(inputLine);
            while (matcher.find()) { // ïîêà åñòü ñîâïàäåíèå
            	String link = inputLine.substring(matcher.start(), matcher.end()); //î÷åðåäíàÿ íàéäåííàÿ ññûëêà
            	if (link.contains("http") == false) {continue;} //îòáðàñûâàåì, åñëè íå íåò ïîäñòðîêè http
            	if (isExist(link) == 404) {deadLinks += link + "\n";} //îïðåäåëÿåì, ñóùåñòâóåò ëè ñòðàíèöà è ñîõðàíÿåì åå, åñëè íå ñóù-åò
            	System.out.println(isExist(link) + " " + link); // âûâîäèì
            }
        }
        in.close();
        
        if (deadLinks == "")
        	System.out.println("íåäåéñòâèòåëüíûõ ññûëîê íå îáíàðóæåíî");
        else
        	System.out.println("\nÍåäåéñòâèòåëüíûå ññûëêè:");
        	System.out.println(deadLinks);
	}
	
	// ìåòîä, êîòîðûé âûÿñíÿåò, ñóùåñòâóåò ëè ñòðàíèöà
	public static int isExist(String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString); // ñîçäàåòñÿ url íà îñíîâå ñòðîêè
	    HttpURLConnection huc =  (HttpURLConnection)url.openConnection(); // óñòàíîâêà ñîåäèíåíèÿ
	    huc.setRequestMethod("GET"); 
	    huc.connect(); 
	    return huc.getResponseCode(); // íåïîñðåäñòâåííî îïðåäåëåíèå 
	}
}
