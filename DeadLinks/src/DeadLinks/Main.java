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
		//�� ���� ��������� ������
		Scanner scanner = new Scanner(System.in);
		System.out.println("�������� ������");
		String url = scanner.nextLine();
		//https://stud.lms.tpu.ru/login/index.php ������ ������
		deadLinks(url); //����� ������
		scanner.close();
	}
	// �����, ���������� ��� ����� ������ ������ � ���� ������
	public static void deadLinks(String urlOfSite) throws IOException {
		//������ html-���� �����
		URL url = new URL(urlOfSite);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String deadLinks = "";//����� ��� ������
        
        String inputLine;
        Matcher matcher;
        Pattern pattern = Pattern.compile("(?<=(?i)href\\s{0,1}=\\s{0,1}\").*?(?=\")");//���������� ��������� ��� ��������� ������
        while ((inputLine = in.readLine()) != null) {//�������� �� ���� ��������
        	matcher = pattern.matcher(inputLine);
            while (matcher.find()) { // ���� ���� ����������
            	String link = inputLine.substring(matcher.start(), matcher.end()); //��������� ��������� ������
            	if (link.contains("http") == false) {continue;} //�����������, ���� �� ��� ��������� http
            	if (isExist(link) == 404) {deadLinks += link + "\n";} //����������, ���������� �� �������� � ��������� ��, ���� �� ���-��
            	System.out.println(isExist(link) + " " + link); // �������
            }
        }
        in.close();
        
        if (deadLinks == "")
        	System.out.println("���������������� ������ �� ����������");
        else
        	System.out.println("\n���������������� ������:");
        	System.out.println(deadLinks);
	}
	
	// �����, ������� ��������, ���������� �� ��������
	public static int isExist(String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString); // ��������� url �� ������ ������
	    HttpURLConnection huc =  (HttpURLConnection)url.openConnection(); // ��������� ����������
	    huc.setRequestMethod("GET"); 
	    huc.connect(); 
	    return huc.getResponseCode(); // ��������������� ����������� 
	}
}
