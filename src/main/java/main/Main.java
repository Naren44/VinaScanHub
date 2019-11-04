/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import function.LoadData;
import function.Scan;
import function.WebCrawlerWithDepth;
import function.removeDup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author Shaco JX
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String url;
        int dept;
        Scan s = new Scan();
        LoadData data = new LoadData();
        data.LoadPayload();
        data.LoadSignature();
        System.out.println("Load Data payload done!");
        System.out.println("Load Data signature done!");
        System.out.println("-----------------------------");
        System.out.println("=> Payload SQLi: "+data.pay_SQLi.size());
        System.out.println("=> Payload XSS: "+data.pay_XSS.size());
        System.out.println("=> Signature SQLi: "+data.sig_SQLi.size());
        System.out.println("=> Signature XSS: "+data.sig_XSS.size());
        System.out.println("================================");
        System.out.println("=        Vina Scan Hub v0.2    =");
        System.out.println("=   Code by Eyes Of God team   =");
        System.out.println("=            Shaco JX          =");
        System.out.println("=             Mr.Lax           =");
        System.out.println("================================");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter url: ");
        url = scan.nextLine();
        System.out.print("Enter level spider: ");
        dept = scan.nextInt();
        scan.nextLine();
        s.Scan(url);
        
        
    }

}
