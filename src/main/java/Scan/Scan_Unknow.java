/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scan;

import Entity.FuzzEntity;
import Entity.VulnEntity;
import PaySig.psSQLi;
import View.VSH;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import function.Scan;
import function.encodeValue;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Shaco JX
 */
public class Scan_Unknow {

    public Scan_Unknow() {
    }

    public void scan_Unknow(Element element, String urlAction, ArrayList<String> payload, String method, CookieManager cooki) throws IOException {
        /* turn off annoying htmlunit warnings */
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        String vulnName = "Other";
        String urlAttack = urlAction;
        boolean checkVuln = false;
        WebRequest requestSettings;
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        if (cooki != null) {
            client.setCookieManager(cooki);
        }
        List<NameValuePair> params;
        List<NameValuePair> paramButton;
       
        encodeValue encodeValue = new encodeValue();
        for (String sPay : payload) {
            try {
                params = new ArrayList<>();
                paramButton = new ArrayList<>();
                if (element == null) {
                    try {
                        String fURL = urlAction.split("\\?")[0];
                        String lURL = urlAction.split("\\?")[1];
                        urlAttack = fURL;

                        for (String s : lURL.split("&")) {
                            String key = s.split("\\=")[0];
                            String value = "";
                            try {
                                value = s.split("\\=")[1] + sPay;
                            } catch (Exception e) {
//                                System.out.println("Error Value attackVulnSQLin: " + e);
                            }
                            params.add(new NameValuePair(key, value));
                        }
                    } catch (Exception e) {
                        //System.out.println("ERROR Case 1: " + e);
                    }
                } else {
                    Elements ele = element.select("input, select, textarea");

                    for (Element e1 : ele) {
                        if (!e1.attr("type").contains("submit") && !e1.attr("type").contains("button")) {
                            params.add(new NameValuePair(e1.attr("name"), sPay));
                        } else {
                            if (e1.attr("name").length() != 0) {
                                paramButton.add(new NameValuePair(e1.attr("name"), encodeValue.encode(e1.attr("value"))));

                            }
                        }
                    }
                }

                if (paramButton.size() == 0) {
                    paramButton.add(new NameValuePair(" ", " "));
                }
                for (NameValuePair nameValuePair : paramButton) {
                    params.add(nameValuePair);
                    try {
                        function.Scan scan = new Scan();
                        if (method.toLowerCase().contains("post")) {
                            requestSettings = new WebRequest(new URL(urlAction), HttpMethod.POST);
                            method = "|POST|";
                            scan.checkURLPOST.add(urlAction);
                        } else {
                            requestSettings = new WebRequest(new URL(urlAttack), HttpMethod.GET);
                            method = "|GET|";
                            scan.checkURLGET.add(urlAttack);
                        }
                        requestSettings.setRequestParameters(params);
                        HtmlPage page = client.getPage(requestSettings);

                        WebResponse response = page.getWebResponse();
                        List<NameValuePair> li = response.getResponseHeaders();
                        FuzzEntity f = new FuzzEntity(urlAction, vulnName, params.toString(), page.asXml(), li.toString());
                        View.VSH.fu.add(f);
                        View.VSH.fuLink.add(urlAction);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    params.remove(nameValuePair);
                }
            } catch (Exception e) {
                System.out.println("ERROR sPay!!!");
                e.printStackTrace();
//                System.out.println("Error attackVulnSQLin: " + urlAction + " ||| " + e);
            }
            if (checkVuln) {
                break;
            }
        }
    }

}
