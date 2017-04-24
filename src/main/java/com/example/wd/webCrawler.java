package com.example.wd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class webCrawler {
	private HashSet<String> links;
	static PrintWriter writer = null;
	ArrayList<String> array = new ArrayList<String>();

	public webCrawler() {
		links = new HashSet<String>();
	}

	public void getPageLinks(String URL, String RootURL) throws IOException {
		if (!links.contains(URL)) {
			try {
				if (links.add(URL)) {
					if(URL.startsWith(RootURL))
						writer.println(URL);
				}
				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");
				for (Element page : linksOnPage) {
					getPageLinks(page.attr("abs:href"), RootURL);
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			} finally {
				writer.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the file path: ");
		String fileName = sc.nextLine();
		System.out.println("Please enter the URL: ");
		String URL = sc.nextLine();

		System.out.println("Please abort the execution by CTRL + C after desired result is obtained.");

		writer = new PrintWriter(fileName, "UTF-8");
		new webCrawler().getPageLinks(URL, URL);
	}

}
				